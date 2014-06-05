package server;

import com.mongodb.Mongo;
import io.dropwizard.lifecycle.Managed;

public class MongoManager implements Managed {
  private Mongo mongoServer;

  public MongoManager( Mongo mongoServer ) {
    this.mongoServer = mongoServer;
  }

  @Override
  public void start() throws Exception {
  }

  @Override
  public void stop() throws Exception {
    mongoServer.close();
  }
}
