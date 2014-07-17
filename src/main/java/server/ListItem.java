package server;

// http://dropwizard.readthedocs.org/en/latest/manual/core.html
// In general, we recommend you separate your projects into three Maven modules: project-api, project-client, and project-application
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import org.joda.time.*;

import java.util.ArrayList;

// This is the domain class for a list item
public class ListItem {
  private String _id;  // Issue making this final?
  @NotBlank
  private String title;
  private String description;
  @URL
  private String url;
  private final DateTime created = new DateTime();
  private ArrayList<ListItem> children = new ArrayList<>();
  // Categorization tags
  // Difficulty/Points/Time
  // Price
  // Author/Artist, Title/Song, Album
  // Done, Due Date, Completion Date (for TO-DO items)

  // Required for deserialization
  public ListItem() {}

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

  public String getUrl() {
    return url;
  }

  public void setUrl( String url ) {
    this.url = url;
  }

  public void addChild( ListItem child ) {
    children.add( child );
  }
}
