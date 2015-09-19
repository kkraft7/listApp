package server;

import java.math.BigDecimal;

// Stores the price of an item in pennies (use this until Java money API is available in 9.x)
public class Price {
  BigDecimal price;

  // Add regular and no-arg constructor?
  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice( BigDecimal newPrice ) {
    price = newPrice;
  }

  // Doubles have precision issues but this should be good enough for my purposes
  // Google offers: com.google.gdata.data.finance.Price
  public void setPriceFromDouble( Double doubleValue ) {
    int dollars = doubleValue.intValue();
    int pennies = new BigDecimal( doubleValue - dollars ).setScale( 2, BigDecimal.ROUND_HALF_UP ).intValue();
    setPrice( new BigDecimal( dollars*100 + pennies ));
  }
}
