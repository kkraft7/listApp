package server.resource;

import net.vz.mongodb.jackson.DBCursor;
import net.vz.mongodb.jackson.JacksonDBCollection;
import server.ListItem;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.codahale.metrics.annotation.Timed;
import server.ListItemCollectionRepresentation;
// import com.yammer.metrics.annotation.Timed;

@Path("/")
public class IndexResource {
  // private final AtomicLong counter = new AtomicLong();
  private JacksonDBCollection<ListItem, String> collection;

  public IndexResource(JacksonDBCollection<ListItem, String> mainList) {
    this.collection = mainList;
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
