package server;

import org.hibernate.validator.constraints.NotBlank;

// Base class for books, songs, records, films, etc.
public class ArtWork extends ListItem {
  @NotBlank
  private String creator; // This is pretty heavily overloaded to represent songwriter, band, author, director...
  @NotBlank
  private String name;
  private PurchaseInfo purchaseInfo;


}
