package com.epam.cdp.spring.service.impl;

import com.epam.cdp.spring.cache.*;
import com.epam.cdp.spring.configuration.*;
import com.epam.cdp.spring.model.*;
import com.epam.cdp.spring.service.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;
import org.springframework.test.context.support.*;
import org.springframework.test.util.*;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InMemoryConfiguration.class, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles(profiles = {"inmemory", "cacheble"})
public class TicketServiceTest {

  @Autowired
  TicketService ticketService;
  @Autowired
  FlightService flightService;
  @Autowired
  CustomerService customerService;

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

  @Test
  public void testPrototypeBeans() throws Exception {
    Object ticketCache = ReflectionTestUtils.getField(ticketService, "serviceCache");
    Object flightCache = ReflectionTestUtils.getField(flightService, "serviceCache");
    Object customerCache = ReflectionTestUtils.getField(customerService, "serviceCache");

    assertTrue(((Cache)ticketCache).isEmpty());

    assertTrue(ticketCache.equals(flightCache));
    assertTrue(flightCache.equals(customerCache));

    assertTrue(ticketCache != flightCache);
    assertTrue(flightCache != customerCache);
  }
}