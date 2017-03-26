package com.epam.cdp.spring.service;

import com.epam.cdp.spring.model.Flight;
import org.joda.time.DateTime;

import java.util.List;

public interface FlightService extends CrudService<Integer, Flight> {
  List<Flight> getAvailableFlights(DateTime from, DateTime to);
}
