package config;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.mongodb.Mongo;
import com.mongodb.DB;
import net.vz.mongodb.jackson.JacksonDBCollection;

import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import server.ListItem;
import server.resource.IndexResource;
import server.MongoHealthCheck;
import server.MongoManager;
import server.resource.ListResource;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.HashMap;

// To add a list (after starting the server):
//   curl -i -X POST -H "Content-Type: application/json" -d '{ "title":"New title", "description":"New list item" }' http://localhost:8080/main-list
// To see existing lists: curl http://localhost:8080
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

  }

  @Override
  public void run(ListConfiguration configuration, Environment environment) throws Exception {
    // Can use the configuration object to pass initialization parameters to the IndexResource
    // Mongo mongoDb = new Mongo( configuration.mongoHost, configuration.mongoPort );
    DB mongoDb = new Mongo().getDB( configuration.mongoDbName );
    JacksonDBCollection<ListItem, String> mainList = JacksonDBCollection.wrap( mongoDb.getCollection("lists"), ListItem.class, String.class);
    environment.jersey().register( new ListResource( mainList ));
    environment.jersey().register( new IndexResource( mainList ));
    environment.lifecycle().manage( new MongoManager( mongoDb.getMongo() ));
    environment.healthChecks().register( "MongoDB health check", new MongoHealthCheck( mongoDb.getMongo() ));
    // Required for handling CORS error in AngularJS (No 'Access-Control-Allow-Origin' header is present)
    // Basically I am getting a cross-site scripting error because the client is on port 9000 and the server is on port 8080
    HashMap<String, String> filterParams = new HashMap<>();
    filterParams.put( "allowedOrigins", "*" );
    filterParams.put( "allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin" );
    filterParams.put( "allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD" );
    FilterHolder filterHolder = new FilterHolder(new CrossOriginFilter());
    filterHolder.setInitParameters(filterParams);
    environment.getApplicationContext().addFilter(filterHolder, "/*", EnumSet.of(DispatcherType.REQUEST));
  }
}
