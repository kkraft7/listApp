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
import server.mongo.MongoHealthCheck;
import server.mongo.MongoManager;
import server.resource.ListResource;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.HashMap;

// Use DB ID or list index to reference individual list items?
// What is the relationship between backend classes and frontend URLs? More-or-less 1:1?
// -- I.e. do we need a REST URL endpoint (and/or a client side type) for every server side type?
// Can we make the transport layer handle "generic" lists?
// To add a list (after starting the server):
//   curl -i -X POST -H "Content-Type: application/json" -d '{ "title":"New title", "description":"New description" }' http://localhost:8080/main-list
// To see existing lists: curl http://localhost:8080/main-list
public class ListApplication extends Application<ListConfiguration> {

  public static void main(String[] args) throws Exception {
    new ListApplication().run(new String[] { "server" });
  }

  @Override
  public String getName() {
    return "List App 1.0";  // Make version number a global constant? Or config property?
  }

  @Override
  public void initialize(Bootstrap<ListConfiguration> bootstrap) {}

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
    // Basically I was getting a cross-site scripting error because the client is on port 9000 and the server is on port 8080
    // Any way to initialize a HashMap in place (define options as a 2D array)?
    HashMap<String, String> filterParams = new HashMap<>();
    // Update this to Dropwizard 0.7 per Ted Young's post at http://jitterted.com/tidbits/2014/09/12/cors-for-dropwizard-0-7-x:
    // Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
    // filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    // filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
    // filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
    // filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
    // filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
    // filter.setInitParameter("allowCredentials", "true");
    filterParams.put( "allowedOrigins", "*" );
    filterParams.put( "allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin" );
    filterParams.put( "allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD" );
    FilterHolder filterHolder = new FilterHolder(new CrossOriginFilter());
    filterHolder.setInitParameters(filterParams);
    environment.getApplicationContext().addFilter(filterHolder, "/*", EnumSet.of(DispatcherType.REQUEST));
  }
}
