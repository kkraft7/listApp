package server;

import org.hibernate.validator.constraints.NotBlank;
import java.net.URL;

public class WebLink {
  @NotBlank
  private URL link;
  private String linkText;
  private String description;

  // Return this as a URI and/or String?
  public URL getLink() {
    return link;
  }

  public void setLink( URL newLink ) {
    link = newLink;
  }

  public String getLinkText() {
    return linkText;
  }

  public void setLinkText( String text ) {
    linkText = text;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription( String newDescription ) {
    description = newDescription;
  }
}
