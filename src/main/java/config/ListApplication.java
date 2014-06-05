package config;

// import com.yammer.dropwizard.Service; // Service -> Application in 7.x
// import com.yammer.dropwizard.config.Bootstrap;
// import com.yammer.dropwizard.config.Environment;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.mongodb.Mongo;
import com.mongodb.DB;
import net.vz.mongodb.jackson.JacksonDBCollection;

import server.ListItem;
import server.resource.IndexResource;
import server.MongoHealthCheck;
import server.MongoManager;
import server.resource.ListResource;

public class ListApplication extends Application<ListConfiguration> {

  public static void main(String[] args) throws Exception {
    new ListApplication().run(new String[] { "server" });
  }

  @Override
  public String getName() {
    return "List App 1.0";  // Make version number a global constant? Or config property?
  }

  @Override
  public void initialize(Bootstrap<ListConfiguration> bootstrap) {
    // bootstrap.setName("blog");   // setName() removed from API in 7.x?
  }

  @Override
  public void run(ListConfiguration configuration, Environment environment) throws Exception {
    // Can use the configuration object to pass initialization parameters to the IndexResource
    // Mongo mongoDb = new Mongo( configuration.mongoHost, configuration.mongoPort );
    DB mongoDb = new Mongo().getDB( configuration.mongoDbName );
    JacksonDBCollection<ListItem, String> mainList = JacksonDBCollection.wrap( mongoDb.getCollection("lists"), ListItem.class, String.class);
    environment.jersey().register( new ListResource( mainList ));
    environment.jersey().register( new IndexResource( mainList ) );
    environment.lifecycle().manage( new MongoManager( mongoDb.getMongo() ));
    environment.healthChecks().register( "MongoDB health check", new MongoHealthCheck( mongoDb.getMongo() ));
  }
}
