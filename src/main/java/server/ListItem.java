package server;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import org.joda.time.DateTime;

import java.util.ArrayList;

// Per Ted /resource/new is an ANTI-PATTERN
// Post to /items -> /items/fec4923
// Create a child of fev4923: -> POST to /items/fec4923 -> items/abc456
// Use PUT for modify
// This is the base class for a list item
public class ListItem {
  private String _id;  // Issue making this final?
  @NotBlank
  private String title;
  private String description;
  private ListItem parent = null;
  private ArrayList<ListItem> children = null;
  private final DateTime creationDate = new DateTime();
  // Categorization tags?

  public ListItem() {}  // Required for deserialization

  public ListItem( String title, String description ) {
    setTitle( title );
    setDescription( description );
  }

  public ListItem( String title, String description, ListItem parent ) {
    this( title, description );
    setParent( parent );
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

  public void setParent( ListItem newListParent ) {
    parent = newListParent;
  }

  public void addItem( ListItem child ) {
    if ( children == null ) {
      children = new ArrayList<>();
    }
    children.add( child );
  }

  public ArrayList<ListItem> getChildren() {
    return children;
  }
}
