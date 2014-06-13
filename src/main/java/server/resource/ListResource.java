package server.resource;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.vz.mongodb.jackson.DBCursor;
import net.vz.mongodb.jackson.JacksonDBCollection;
import com.codahale.metrics.annotation.Timed;
import server.ListItem;
import server.ListItemCollectionRepresentation;

import java.util.ArrayList;
import java.util.List;

@Path("/main-list")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ListResource {
  private JacksonDBCollection<ListItem, String> collection;

  public ListResource( JacksonDBCollection<ListItem, String> mainList ) {
    this.collection = mainList;
  }

  @POST
  @Timed
  public Response publishNewBlog( @Valid ListItem newItem ) {
    collection.insert( newItem );
    return Response.noContent().build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Timed
  public ListItemCollectionRepresentation index() {
    DBCursor<ListItem> dbCursor = collection.find();
    List<ListItem> mainList = new ArrayList<>();
    while (dbCursor.hasNext()) {
      ListItem item = dbCursor.next();
      mainList.add(item);
    }
    return new ListItemCollectionRepresentation(mainList);
  }
}
