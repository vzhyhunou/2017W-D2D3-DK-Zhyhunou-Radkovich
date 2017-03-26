package com.epam.cdp.spring.dao.impl.inmemory;

import com.epam.cdp.spring.dao.FlightRepository;
import com.epam.cdp.spring.model.Flight;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class FlightInMemoryRepositoryTest {
  @Test
  public void getAvailableFlights() throws Exception {
    FlightRepository repository = new FlightInMemoryRepository();
    repository.create(new Flight(1, DateTime.parse("2000-11-1")));
    repository.create(new Flight(2, DateTime.parse("2000-11-3")));
    repository.create(new Flight(2, DateTime.parse("2000-11-5")));
    repository.create(new Flight(2, DateTime.parse("2000-11-6")));

    DateTime from = DateTime.parse("2000-11-2");
    DateTime to = DateTime.parse("2000-11-6");

    List<Flight> availableFlights = repository.getAvailableFlights(from, to);
    assertEquals(2, availableFlights.size());
  }
}