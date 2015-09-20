package server.resource;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.*;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import net.vz.mongodb.jackson.DBCursor;
import net.vz.mongodb.jackson.JacksonDBCollection;
import com.codahale.metrics.annotation.Timed;
import org.bson.types.ObjectId;

import server.Completable;
import server.ListItem;
import server.representation.ListRepresentation;
import server.representation.CompletableRepresentation;


@Path("/completables")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompletableResource extends ListResource {
  private static Logger log = LoggerFactory.getLogger(ListResource.class);
  private JacksonDBCollection<Completable, String> completables; // Looks like this magically stays in sync with the DB

  public CompletableResource( JacksonDBCollection<Completable, String> items ) {
    this.completables = items;
  }

  @Path("/")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Timed
  // Should we factor GET and POST methods into a generic resource base class (URLs wouldn't work?)
  // DO ALL THIS STUFF IN THE BASE CLASS (ListResource)? PASS newItem TO IT?
  public Response publishNewCompletable( @PathParam("id") String id, @Valid Completable newItem ) {
    ListItem item = getList( id );
    item.getChildren().add( newItem );
    completables.insert( newItem );
    return Response.noContent().build();
  }

  @Path("/{id}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Timed
  public CompletableRepresentation getCompletable( @PathParam("id") String id ) {
    // Magic from Ted for retrieving an individual list item from the DB:
    DBObject dbItemId = new BasicDBObject("_id", new ObjectId( id ));
    Completable item = completables.findOne( dbItemId );
    System.out.println( "LIST ITEM (ID = " + id + "): " + item );
    try {
      return new CompletableRepresentation( item );
    }
    catch ( MongoException ex ) {
      log.error( "Error accessing Mongo DB", ex );
      return null;  // NOT SURE HOW BEST TO HANDLE THIS
    }
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Timed
  // Change name to viewCompletables()?
  // Just return a list of IDs and Titles and use getListItem() to get the details?
  public ListRepresentation index() {
    DBCursor<Completable> dbCursor = completables.find();
    List<ListItem> completableList = new ArrayList<>();
    while (dbCursor.hasNext()) {
      Completable item = dbCursor.next();
      completableList.add(item);
    }
    return new ListRepresentation(completableList); // Will need to "cast" this to Completable on the client side?
  }
}
