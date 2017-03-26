package com.epam.cdp.spring.dao;

import com.epam.cdp.spring.dao.impl.inmemory.CrudRepository;
import com.epam.cdp.spring.model.Flight;
import org.joda.time.DateTime;

import java.util.List;

public interface FlightRepository extends CrudRepository<Integer, Flight> {
  List<Flight> getAvailableFlights(DateTime from, DateTime to);
}
