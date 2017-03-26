package com.epam.cdp.spring.service;

import com.epam.cdp.spring.configuration.InMemoryConfiguration;
import com.epam.cdp.spring.model.Ticket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InMemoryConfiguration.class, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("inmemory")
public class TicketServiceTest {

  @Autowired
  TicketService ticketService;

  @Before
  public void setUp() throws Exception {
    ticketService.create(new Ticket(1, 1, 1, 1, true));
    ticketService.create(new Ticket(1, 2, 2, 2, false));
    ticketService.create(new Ticket(1, 3, 3, 3, false));
    ticketService.create(new Ticket(2, 4, 4, 4, true));
  }

  @After
  public void tearDown() throws Exception {
    Arrays.asList(1, 2, 3, 4).forEach(id -> ticketService.delete(id));
  }

  @Test
  public void getAllForFlight() throws Exception {
    List<Ticket> allForFlight = ticketService.getAllForFlight(1);
    assertEquals(3, allForFlight.size());
  }

  @Test
  public void getAvailableTicketsForFlight() throws Exception {
    List<Ticket> availableTickets = ticketService.getAvailableTicketsForFlight(1);
    assertEquals(2, availableTickets.size());

    List<Ticket> availableTickets2 = ticketService.getAvailableTicketsForFlight(2);
    assertEquals(0, availableTickets2.size());
  }
}