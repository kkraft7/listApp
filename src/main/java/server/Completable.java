package server;

import org.hibernate.validator.constraints.NotBlank;

public class Completable extends ListItem {
  @NotBlank
  private boolean completed = false;

  // Required for deserialization
  public Completable() {}

  public Completable( boolean completed ) {
    setCompleted( completed );
  }

  public boolean getCompleted() {
    return completed;
  }

  public void setCompleted( boolean completionValue ) {
    completed = completionValue;
  }
}
