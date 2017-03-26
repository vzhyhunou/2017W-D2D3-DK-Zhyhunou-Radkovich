package com.epam.cdp.spring.model;

import org.joda.time.DateTime;

public class Flight {
  public int flightId;
  public DateTime dataOfFlight;

  public Flight(int flightId, DateTime dataOfFlight) {
    this.flightId = flightId;
    this.dataOfFlight = dataOfFlight;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Flight flight = (Flight) o;

    if (flightId != flight.flightId) return false;
    return dataOfFlight != null ? dataOfFlight.equals(flight.dataOfFlight) : flight.dataOfFlight == null;

  }

  @Override
  public int hashCode() {
    int result = flightId;
    result = 31 * result + (dataOfFlight != null ? dataOfFlight.hashCode() : 0);
    return result;
  }
}
