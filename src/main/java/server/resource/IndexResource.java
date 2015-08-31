package server.resource;

import net.vz.mongodb.jackson.JacksonDBCollection;
import server.ListItem;

import javax.ws.rs.Path;

@Path("/")
public class IndexResource {
  // Add handler code to this once I decide what the root URL should do
  private JacksonDBCollection<ListItem, String> collection;

  public IndexResource(JacksonDBCollection<ListItem, String> mainList) {
    this.collection = mainList;
  }

/*
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
*/
}
