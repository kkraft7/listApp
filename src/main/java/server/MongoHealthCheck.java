package server;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.Mongo;

public class MongoHealthCheck extends HealthCheck {
  private Mongo mongoServer;

  public MongoHealthCheck( Mongo mongoServer ) {
    this.mongoServer = mongoServer;
  }

  @Override
  protected Result check() throws Exception {
    // Print a log line here?
    mongoServer.getDatabaseNames();
    return Result.healthy();
  }
}
