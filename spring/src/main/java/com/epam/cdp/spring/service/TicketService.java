package com.epam.cdp.spring.service;

import com.epam.cdp.spring.model.Ticket;

import java.util.List;

public interface TicketService extends CrudService<Integer, Ticket> {
  List<Ticket> getAllForFlight(int flightId);
  List<Ticket> getAvailableTicketsForFlight(int flightId);
}
