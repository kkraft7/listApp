package server;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import org.joda.time.DateTime;

import java.util.ArrayList;

// This is the base class for a list item
public class ListItem {
  private String _id;  // Issue making this final?
  @NotBlank
  private String title;
  private String description;
  private ListItem parent = null;
  private ArrayList<ListItem> children = new ArrayList<>();
  private final DateTime creationDate = new DateTime();
  // Categorization tags?

  // Required for deserialization
  public ListItem() {}

  // Create additional constructors...
  public ListItem( String title, String description ) {
    setTitle( title );
    setDescription( description );
  }

  // Per Ted let the DB define the object ID
  @JsonProperty("_id")
  public String getId() {
    return _id;
  }

  public void setId(String id) {
    this._id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle( String title ) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription( String description ) {
    this.description = description;
  }

  public ListItem getParent() {
    return parent;
  }

  public void setParent( ListItem newParent ) {
    parent = newParent;
  }

  public void addChild( ListItem child ) {
    children.add( child );
  }

  public ArrayList<ListItem> getChildren() {
    return children;
  }
}
