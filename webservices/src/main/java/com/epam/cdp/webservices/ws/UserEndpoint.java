package com.epam.cdp.webservices.ws;

import com.epam.cdp.webservices.repository.UserRepository;
import by.epam.GetUserEmailRequest;
import by.epam.GetUserEmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class UserEndpoint {
  public static final String NAMESPACE_URI = "http://epam.by";

  private UserRepository repository;

  @Autowired
  public UserEndpoint(UserRepository repository) {
    this.repository = repository;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserEmailRequest")
  @ResponsePayload
  public GetUserEmailResponse getCountry(@RequestPayload GetUserEmailRequest request) {
    GetUserEmailResponse response = new GetUserEmailResponse();
    response.setEmail(repository.findEmailByName(request.getName()));

    return response;
  }
}
