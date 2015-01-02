package server;

import java.util.List;
import java.util.ArrayList;

import org.hibernate.validator.constraints.NotBlank;

// Class for adding purchasing data to an item
public class PurchaseInfo {
  // Apparently double has rounding issues but creating/supporting a separate BigDecimal price class is overkill right now
  @NotBlank
  private double price;
  private List<WebLink> vendorLinks = new ArrayList<>();

  public double getPrice() {
    return price;
  }

  public void setPrice( double newPrice ) {
    price = newPrice;
  }

  public List<WebLink> getVendorLinks() {
    return vendorLinks;
  }

  public void addVendorLink( WebLink link ) {
    vendorLinks.add( link );
  }
}
