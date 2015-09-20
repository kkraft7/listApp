package server.resource;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import net.vz.mongodb.jackson.DBCursor;
import net.vz.mongodb.jackson.JacksonDBCollection;
import server.ListItem;
import server.representation.ListItemRepresentation;
import server.representation.ListRepresentation;

import java.util.ArrayList;
import java.util.List;

@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
public class ListItemResource extends ListResource {
  private JacksonDBCollection<ListItem, String> listItems; // Looks like this magically stays in sync with the DB

  @Path("/")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Timed
  public Response createNewList( @Valid ListItem newListItem ) {
    listItems.insert( newListItem );
    ListItem parent = getList( newListItem.getParent().getId() );
    parent.getChildren().add( newListItem );
    return Response.noContent().build();
  }

  // Display the specified list item
  @Path("/{id}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Timed
  public ListItemRepresentation getListItem( @PathParam("id") String id ) {
    return super.getListItem( id );
  }

  // Display all list items
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Timed
  // Change name to viewListItems() or getListItems()?
  public ListRepresentation index() {
    DBCursor<ListItem> dbCursor = listItems.find();
    List<ListItem> allListItems = new ArrayList<>();
    while (dbCursor.hasNext()) {
      ListItem item = dbCursor.next();
      allListItems.add(item);
    }
    return new ListRepresentation(allListItems);
  }
}
