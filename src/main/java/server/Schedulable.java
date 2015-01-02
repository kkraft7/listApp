package server;

import org.joda.time.DateTime;

public class Schedulable extends Completable {
  DateTime eventDate;

  public DateTime getEventDate() {
    return eventDate;
  }

  public void setEventDate( DateTime newDate ) {
    eventDate = newDate;
  }
}
