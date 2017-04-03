package com.epam.cdp.spring.service.impl;

import com.epam.cdp.spring.cache.*;
import com.epam.cdp.spring.dao.TicketRepository;
import com.epam.cdp.spring.model.Ticket;
import com.epam.cdp.spring.service.TicketService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TicketServiceImpl implements TicketService {

  @Autowired
  TicketRepository ticketRepository;

  @Qualifier("serviceCache")
  @Autowired
  Cache serviceCache;

  @Override
  public void create(Ticket obj) {
    ticketRepository.create(obj);
  }

  @Override
  public Ticket read(Integer id) {
    return ticketRepository.read(id, Ticket.class);
  }

  @Override
  public Ticket update(Integer id, Ticket obj) {
    return ticketRepository.update(id, obj);
  }

  @Override
  public Ticket delete(Integer id) {
    return ticketRepository.delete(id, Ticket.class);
  }

  public List<Ticket> getAllForFlight(int flightId) {
    return ticketRepository.getAllForFlight(flightId);
  }

  public List<Ticket> getAvailableTicketsForFlight(int flightId) {
    return ticketRepository.getAvailableTicketsForFlight(flightId);
  }
}
