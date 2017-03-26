package com.epam.cdp.spring.dao.impl.inmemory;

import com.epam.cdp.spring.dao.TicketRepository;
import com.epam.cdp.spring.model.Ticket;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class TickerInMemoryRepository
    extends AbstractInMemoryStorage<Integer, Ticket>
    implements TicketRepository {

  private Integer incrementalId = 1;

  @Override
  public List<Ticket> getAllForFlight(int flightId) {
    Predicate<Ticket> byFlightId = ticket -> ticket.flightId == flightId;
    return storage.values()
        .stream()
        .filter(byFlightId)
        .collect(Collectors.toList());
  }

  @Override
  public List<Ticket> getAvailableTicketsForFlight(int flightId) {
    Predicate<Ticket> isNotBooked = ticket -> ticket.flightId == flightId && !ticket.isBooked;
    return storage.values()
        .stream()
        .filter(isNotBooked)
        .collect(Collectors.toList());
  }

  @Override
  Integer generateId() {
    return incrementalId++;
  }
}
