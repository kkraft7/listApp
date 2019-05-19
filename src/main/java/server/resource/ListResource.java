package server.resource;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import net.vz.mongodb.jackson.DBCursor;
import net.vz.mongodb.jackson.JacksonDBCollection;
import com.codahale.metrics.annotation.Timed;
import org.bson.types.ObjectId;
import server.ListItem;
import server.representation.ListRepresentation;
import server.representation.ListItemRepresentation;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.*;

// I think /lists and listItems represent the de-facto top-level lists
// Initialize a special root list and add list items with no parent to it?
// - Can I "manually" save it to the database (and retain its ID)?
// Create a ListItemResource extending ListResource with URL /lists?
// - Parent must be null for ListResource
@Path("/lists")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ListResource {
  private static Logger log = LoggerFactory.getLogger(ListResource.class);
  // Call this "lists" or "topLevelLists"
  private JacksonDBCollection<ListItem, String> listItems; // Looks like this magically stays in sync with the DB

  public ListResource( JacksonDBCollection<ListItem, String> items ) {
    this.listItems = items;
  }
  public ListResource() {}

  // Add a new top-level list (WILL THIS WORK?!)
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Timed
  public Response createNewList( @Valid ListItem newList ) {
    listItems.insert( newList );
    return Response.noContent().build();
  }

  // WILL THIS APPROACH WORK?!
  // Add a new ListItem to the specified list
/*
  @Path("/list-{id}/new")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Timed
  // Should this reside at a different URL (e.g. lists/new)?
  // Should we factor GET and POST methods into a generic resource base class (URLs wouldn't work?)
  public Response publishNewListItem( @PathParam("id") String id, @Valid ListItem newItem ) {
    DBObject dbItemId = new BasicDBObject("_id", new ObjectId( id ));
    ListItem item = listItems.findOne( dbItemId );
    item.getChildren().add( newItem );
    return Response.noContent().build();
  }
*/

  // Display the specified list
  @Path("/{id}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Timed
  public ListItemRepresentation getListItem( @PathParam("id") String id ) {
    // Magic from Ted for retrieving an individual list item from the DB:
    DBObject dbItemId = new BasicDBObject("_id", new ObjectId( id ));
    ListItem list = listItems.findOne( dbItemId );
    System.out.println( "LIST ITEM (ID = " + id + "): " + list );
    try {
      return new ListItemRepresentation( list );
    }
    catch ( MongoException ex ) {
      log.error( "Error accessing Mongo DB", ex );
      return null;  // NOT SURE HOW BEST TO HANDLE THIS
    }
  }

  // Display all top level lists
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Timed
  // Change name to viewLists() or getLists()?
  public ListRepresentation index() {
    DBCursor<ListItem> dbCursor = listItems.find();
    List<ListItem> mainList = new ArrayList<>();
    while (dbCursor.hasNext()) {
      ListItem item = dbCursor.next();
      mainList.add(item);
    }
    return new ListRepresentation(mainList);
  }

  // PUT THIS IN A TOP-LEVEL CLASS (ListBase?) THAT THIS CLASS WOULD EXTEND SO ALL SUBCLASSES COULD INHERIT?
  // FACTOR OUT DUPLICATE CODE, ABOVE
  // getListById(id) - put in a base or Utility class?
  public ListItem getList( String id ) {
    DBObject dbItemId = new BasicDBObject("_id", new ObjectId( id ));
    return listItems.findOne( dbItemId );
  }
}
