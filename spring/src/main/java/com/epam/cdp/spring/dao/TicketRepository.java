package com.epam.cdp.spring.dao;

import com.epam.cdp.spring.dao.impl.inmemory.CrudRepository;
import com.epam.cdp.spring.model.Ticket;

import java.util.List;

public interface TicketRepository extends CrudRepository<Integer, Ticket> {
  List<Ticket> getAllForFlight(int flightId);
  List<Ticket> getAvailableTicketsForFlight(int flightId);
}
