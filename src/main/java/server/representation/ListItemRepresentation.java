package server.representation;

// http://dropwizard.readthedocs.org/en/latest/manual/core.html
// In general, we recommend you separate your projects into three Maven modules: project-api, project-client, and project-application
// import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import server.ListItem;

// This is a read-only representation of a list item
public class ListItemRepresentation {
  @NotBlank
  private String id;  // Issue making this final?
  @NotBlank
  private String title;
  private String description;
  private List<ListItem> children;

  public ListItemRepresentation( ListItem item ) {
    this.id = item.getId();
    this.title = item.getTitle();
    this.description = item.getDescription();
    this.children = item.getChildren();
  }

  public ListItemRepresentation() {}

  // Per Ted let the DB define the object ID
  @JsonProperty
  public String getId() {
    return id;
  }

  @JsonProperty
  public String getTitle() {
    return title;
  }

  @JsonProperty
  public String getDescription() {
    return description;
  }

  // Will have to check for null here (for a leaf item)
  @JsonProperty
  public List<ListItem> getChildren() { return children; }
}
