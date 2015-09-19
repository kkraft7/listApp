package server.representation;

import server.ListItem;

import java.util.List;

public class ListItemCollectionRepresentation {
  public List<ListItem> listItems;

  public ListItemCollectionRepresentation(List<ListItem> listItems) {
    this.listItems = listItems;
  }

  public String toString() {
    StringBuilder result = new StringBuilder();
    for ( ListItem item : listItems ) {
      result.append( item.toString() + "\n" );
    }
    return result.toString();
  }
}
