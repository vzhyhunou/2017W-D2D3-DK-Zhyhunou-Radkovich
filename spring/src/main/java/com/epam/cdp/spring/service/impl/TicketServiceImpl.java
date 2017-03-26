package com.epam.cdp.spring.service.impl;

import com.epam.cdp.spring.dao.TicketRepository;
import com.epam.cdp.spring.model.Ticket;
import com.epam.cdp.spring.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

  @Autowired
  TicketRepository ticketRepository;

  @Override
  public void create(Ticket obj) {
    ticketRepository.create(obj);
  }

  @Override
  public Ticket read(Integer id) {
    return ticketRepository.read(id);
  }

  @Override
  public Ticket update(Integer id, Ticket obj) {
    return ticketRepository.update(id, obj);
  }

  @Override
  public Ticket delete(Integer id) {
    return ticketRepository.delete(id);
  }

  public List<Ticket> getAllForFlight(int flightId) {
    return ticketRepository.getAllForFlight(flightId);
  }

  public List<Ticket> getAvailableTicketsForFlight(int flightId) {
    return ticketRepository.getAvailableTicketsForFlight(flightId);
  }
}
