package com.epam.cdp.spring.dao.impl.inmemory;

import com.epam.cdp.spring.dao.TicketRepository;
import com.epam.cdp.spring.model.Ticket;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TickerInMemoryRepositoryTest {

  TicketRepository repository;

  @Before
  public void setUp() throws Exception {
    repository = new TickerInMemoryRepository();
    repository.create(new Ticket(1, 1, 1, 1, true));
    repository.create(new Ticket(1, 2, 2, 2, false));
    repository.create(new Ticket(1, 3, 3, 3, false));
    repository.create(new Ticket(2, 4, 4, 4, true));
  }

  @Test
  public void getAllForFlight() throws Exception {
    List<Ticket> allForFlight = repository.getAllForFlight(1);
    assertEquals(3, allForFlight.size());
  }

  @Test
  public void getAvailableTicketsForFlight() throws Exception {
    List<Ticket> availableTickets = repository.getAvailableTicketsForFlight(1);
    assertEquals(2, availableTickets.size());

    List<Ticket> availableTickets2 = repository.getAvailableTicketsForFlight(2);
    assertEquals(0, availableTickets2.size());
  }
}
