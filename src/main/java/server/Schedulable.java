package server;

import org.joda.time.DateTime;

public class Schedulable extends Completable {
  DateTime eventDate;

  // Required for deserialization
  public Schedulable() {}

  public Schedulable( DateTime eventDate ) {
    super( false );
    setEventDate( eventDate );
  }

  public DateTime getEventDate() {
    return eventDate;
  }

  public void setEventDate( DateTime newDate ) {
    eventDate = newDate;
  }
}
