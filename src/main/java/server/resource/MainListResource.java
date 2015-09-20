package server.resource;

import com.codahale.metrics.annotation.Timed;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import net.vz.mongodb.jackson.DBCursor;
import net.vz.mongodb.jackson.JacksonDBCollection;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.ListItem;
import server.representation.ListRepresentation;
import server.representation.ListItemRepresentation;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

// THIS IS A LEGACY CLASS, RETAINED FOR BACKWARDS COMPATIBILITY WITH THE CURRENT UI
// SHOULD I HAVE A PARALLEL HIERARCHY FOR RESOURCES AND REPRESENTATIONS?
@Path("/main-list") // items, lists
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MainListResource {
  private static Logger log = LoggerFactory.getLogger(MainListResource.class);
  private JacksonDBCollection<ListItem, String> listItems; // Looks like this magically stays in sync with the DB

  public MainListResource(JacksonDBCollection<ListItem, String> mainList) {
    this.listItems = mainList;
  }

  @POST
  @Timed
  // Should this reside at a different URL (e.g. new-item)? ListItemResource?
  // Should we factor GET and POST methods into a generic resource base class (URLs wouldn't work?)
  public Response publishNewListItem( @Valid ListItem newItem ) {
    listItems.insert( newItem );
    return Response.noContent().build();
  }

  @Path("/item-{id}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Timed
  public ListItemRepresentation getListItem( @PathParam("id") String id ) {
    // System.out.println( "LIST ITEM ID: " + id );
    // Magic from Ted for retrieving an individual list item from the DB:
    DBObject dbItemId = new BasicDBObject("_id", new ObjectId( id ));
    ListItem item = listItems.findOne( dbItemId );
    System.out.println( "LIST ITEM: " + item );
    try {
      return new ListItemRepresentation( item );
    }
    catch ( MongoException ex ) {
      log.error( "Error accessing Mongo DB", ex );
      return null;  // NOT SURE HOW BEST TO HANDLE THIS
    }
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Timed
  // Change name to viewListItems() (get() would be too implementation-specific)?
  // Just return a list of IDs and Titles and use getListItem() to get the details?
  // EVENTUALLY THIS (THE TOP LEVEL) WILL RETURN A LIST OF LISTS?
  public ListRepresentation index() {
    DBCursor<ListItem> dbCursor = listItems.find();
    List<ListItem> mainList = new ArrayList<>();
    while (dbCursor.hasNext()) {
      ListItem item = dbCursor.next();
      mainList.add(item);
    }
    return new ListRepresentation(mainList);
  }
}
