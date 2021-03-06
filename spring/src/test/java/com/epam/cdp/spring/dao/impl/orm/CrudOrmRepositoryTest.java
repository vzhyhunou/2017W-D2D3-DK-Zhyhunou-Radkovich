package com.epam.cdp.spring.dao.impl.orm;

import com.epam.cdp.spring.configuration.InMemoryConfiguration;
import com.epam.cdp.spring.model.Customer;
import com.epam.cdp.spring.model.Flight;
import com.epam.cdp.spring.model.Ticket;
import org.joda.time.DateTime;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InMemoryConfiguration.class, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles(profiles = {"h2", "cacheble", "inmemory"})
public class CrudOrmRepositoryTest {

  @Autowired
  private CrudOrmRepository repository;

  @Test
  public void createCustomer() throws Exception {
    Customer expectedCustomer1 = new Customer(1, "firstname1", "lastname1");
    Customer expectedCustomer2 = new Customer(2, "firstname2", "lastname2");

    repository.create(expectedCustomer1);
    repository.create(expectedCustomer2);

    List<Customer> customer1 = repository.read(new Customer(1), Customer.class);
    List<Customer> customer2 = repository.read(new Customer(2), Customer.class);

    assertEquals(singletonList(expectedCustomer1), customer1);
    assertEquals(singletonList(expectedCustomer2), customer2);
  }

  @Test
  public void updateCustomer() throws Exception {
    Customer customer3 = new Customer(3, "firstname3", "lastname3");
    Customer customer4 = new Customer(4, "firstname4", "lastname4");

    repository.create(customer3);
    repository.create(customer4);

    String newFirstName = "new_first_name";
    String newLastName = "new_last_name";

    Customer sample = new Customer();
    sample.firstName = newFirstName;
    sample.lastName = newLastName;

    Customer condition = new Customer(3);
    repository.update(condition, sample);

    List<Customer> customerList = repository.read(condition, Customer.class);

    assertTrue(customerList.size() == 1);
    assertEquals(customerList.get(0), new Customer(3, newFirstName, newLastName));
  }

  @Test
  public void deleteCustomer() throws Exception {
    Customer customer5 = new Customer(5, "firstname3", "lastname4");
    Customer customer6 = new Customer(6, "firstname", "lastname4");
    Customer customer7 = new Customer(7, "firstname", "lastname4");

    repository.create(customer5);
    repository.create(customer6);
    repository.create(customer7);

    Customer condition = new Customer();
    condition.firstName = "firstname";
    repository.delete(condition);

    List<Customer> customerList = repository.read(new Customer(null, null, "lastname4"), Customer.class);
    assertEquals(singletonList(customer5), customerList);
  }

  @Test
  public void createReadFlight() throws Exception {
    Flight flight1 = new Flight(1, DateTime.parse("2000-11-11"));

    repository.create(flight1);

    List<Flight> flights = repository.read(new Flight(1, null), Flight.class);
    assertTrue(!flights.isEmpty());
    assertEquals(flight1, flights.get(0));
  }

  @Test
  public void createReadTicket() throws Exception {
    Ticket ticket1 = new Ticket(1, 1, 1, 1, true);
    Ticket ticket2 = new Ticket(1, 1, 2, 2, true);
    Ticket ticket3 = new Ticket(1, 1, 3, 3, false);

    repository.create(ticket1);
    repository.create(ticket2);
    repository.create(ticket3);

    Ticket ticketActual1 = repository.read(new Ticket(null, null, 1, null, null), Ticket.class).get(0);
    Ticket ticketActual2 = repository.read(new Ticket(null, null, 2, null, null), Ticket.class).get(0);
    Ticket ticketActual3 = repository.read(new Ticket(null, null, 3, null, null), Ticket.class).get(0);

    assertEquals(ticket1, ticketActual1);
    assertEquals(ticket2, ticketActual2);
    assertEquals(ticket3, ticketActual3);
  }
}
