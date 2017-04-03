package com.epam.cdp.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMERS")
public class Customer {
  @Column(name = "customerId")
  public Integer customerId;
  @Column(name = "firstName", length = 30)
  public String firstName;
  @Column(name = "lastName", length = 30)
  public String lastName;

  public Customer() {
  }

  public Customer(Integer customerId) {
    this.customerId = customerId;
  }

  public Customer(Integer customerId, String firstName, String lastName) {
    this.customerId = customerId;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Customer customer = (Customer) o;

    if (customerId != customer.customerId) return false;
    if (firstName != null ? !firstName.equals(customer.firstName) : customer.firstName != null) return false;
    return lastName != null ? lastName.equals(customer.lastName) : customer.lastName == null;

  }

  @Override
  public int hashCode() {
    int result = customerId;
    result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Customer{" +
        "customerId=" + customerId +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        '}';
  }
}
