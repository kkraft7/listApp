package config;

// import com.yammer.dropwizard.config.Configuration;
import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class ListConfiguration extends Configuration {
  @JsonProperty
  @NotEmpty
  public String mongoHost = "localhost";

  @JsonProperty
  @Min(1)
  @Max(65535)  public int mongoPort = 27017;

  // CONSTRUCTOR DOESN'T TAKE THIS! HOW DO I SPECIFY THE DB NAME?!
  @JsonProperty
  @NotEmpty
  public String mongoDbName = "listAppAlphaDb";

  // @NotEmpty
  private String configProperty1;

  @JsonProperty
  public String getConfigProperty1() {
    return configProperty1;
  }

  @JsonProperty
  public void setConfigProperty1(String property) {
    this.configProperty1 = property;
  }
}
