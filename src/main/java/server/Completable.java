package server;

import org.hibernate.validator.constraints.NotBlank;

public class Completable extends ListItem {
  @NotBlank
  private boolean completed = false;

  public boolean getCompleted() {
    return completed;
  }

  public void setCompleted( boolean completionValue ) {
    completed = completionValue;
  }
}
