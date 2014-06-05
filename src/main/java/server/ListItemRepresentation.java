package server;

// http://dropwizard.readthedocs.org/en/latest/manual/core.html
// In general, we recommend you separate your projects into three Maven modules: project-api, project-client, and project-application
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

// This is a read-only representation of a list item
public class ListItemRepresentation {
  @NotBlank
  private String id;  // Issue making this final?
  @NotBlank
  private String title;
  private String description;

  public ListItemRepresentation( ListItem item ) {
    this.id = item.getId();
    this.title = item.getTitle();
    this.description = item.getDescription();
  }

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
}
