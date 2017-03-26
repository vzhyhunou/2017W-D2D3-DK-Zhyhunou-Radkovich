package com.epam.cdp.spring.dao.impl.inmemory;

import com.epam.cdp.spring.dao.CustomerRepository;
import com.epam.cdp.spring.dao.FlightRepository;
import com.epam.cdp.spring.model.Customer;
import com.epam.cdp.spring.model.Flight;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class FlightInMemoryRepository
    extends AbstractInMemoryStorage<Integer, Flight>
    implements FlightRepository {

  private Integer incrementalId = 1;

  @Override
  Integer generateId() {
    return incrementalId++;
  }

  @Override
  public List<Flight> getAvailableFlights(DateTime from, DateTime to) {
    Predicate<Flight> between = flight -> flight.dataOfFlight.isAfter(from) && flight.dataOfFlight.isBefore(to);
    return storage.values().stream()
        .filter(between)
        .collect(Collectors.toList());
  }
}
