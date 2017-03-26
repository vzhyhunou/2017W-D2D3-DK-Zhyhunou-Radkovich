package com.epam.cdp.spring.model;

public class Ticket {
  public int flightId;
  public int customerId;
  public int ticketId;
  public int place;
  public boolean isBooked;

  public Ticket(int flightId, int customerId, int ticketId, int place, boolean isBooked) {
    this.flightId = flightId;
    this.customerId = customerId;
    this.ticketId = ticketId;
    this.place = place;
    this.isBooked = isBooked;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Ticket ticket = (Ticket) o;

    if (flightId != ticket.flightId) return false;
    if (customerId != ticket.customerId) return false;
    if (ticketId != ticket.ticketId) return false;
    if (place != ticket.place) return false;
    return isBooked == ticket.isBooked;

  }

  @Override
  public int hashCode() {
    int result = flightId;
    result = 31 * result + customerId;
    result = 31 * result + ticketId;
    result = 31 * result + place;
    result = 31 * result + (isBooked ? 1 : 0);
    return result;
  }
}
