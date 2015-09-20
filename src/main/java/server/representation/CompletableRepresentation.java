package server.representation;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import server.Completable;

public class CompletableRepresentation extends ListItemRepresentation {
  @NotBlank
  private String id;  // Issue making this final?
  @NotBlank
  private boolean completed;

  public CompletableRepresentation( Completable item ) {
    super( item );
    this.completed = item.getCompleted();
  }

  // Per Ted let the DB define the object ID
  @JsonProperty
  public String getId() {
    return id;
  }

  @JsonProperty
  public boolean getCompleted() {
    return completed;
  }
}
