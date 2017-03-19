package com.epam.cdp.webservices.ws;

import com.epam.cdp.webservices.repository.UserRepository;
import io.spring.guides.gs_producing_web_service.GetUserEmailRequest;
import io.spring.guides.gs_producing_web_service.GetUserEmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class UserEndpoint {
  private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

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
