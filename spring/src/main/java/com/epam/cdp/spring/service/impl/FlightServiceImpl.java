package com.epam.cdp.spring.service.impl;

import com.epam.cdp.spring.cache.*;
import com.epam.cdp.spring.dao.FlightRepository;
import com.epam.cdp.spring.model.Flight;
import com.epam.cdp.spring.service.FlightService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FlightServiceImpl implements FlightService {

  @Autowired
  FlightRepository flightRepository;

  @Qualifier("serviceCache")
  @Autowired
  Cache serviceCache;

  @Override
  public void create(Flight obj) {
    flightRepository.create(obj);
  }

  @Override
  public Flight read(Integer id) {
    return flightRepository.read(id, Flight.class);
  }

  @Override
  public Flight update(Integer id, Flight obj) {
    return flightRepository.update(id, obj);
  }

  @Override
  public Flight delete(Integer id) {
    return flightRepository.delete(id, Flight.class);
  }

  @Override
  public List<Flight> getAvailableFlights(DateTime from, DateTime to) {
    return flightRepository.getAvailableFlights(from, to);
  }
}
