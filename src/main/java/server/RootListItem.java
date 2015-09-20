package server;

import net.vz.mongodb.jackson.JacksonDBCollection;
import org.slf4j.*;

// I THINK THIS MAY NOT BE NECESSARY
public class RootListItem {
  private static JacksonDBCollection<ListItem, String> root = null;
  private static Logger log = LoggerFactory.getLogger(RootListItem.class);
//  private JacksonDBCollection<ListItem, String> rootListItems; // Looks like this magically stays in sync with the DB

  protected RootListItem() {} // Prevent unauthorized instantiation

  public static ListItem getRoot() {
    if ( root == null ) {
//      root.getDbCollection()
      ListItem rootItem = new ListItem( "Root List Item", "This ListItem represents the root of the list hierarchy" );
//      root =  // Can't figure out how to create a new JacksonDBCollection object on demand
    }
//    return root;
    return null;
  }

  public static void addItem( ListItem item ) {

  }
}
