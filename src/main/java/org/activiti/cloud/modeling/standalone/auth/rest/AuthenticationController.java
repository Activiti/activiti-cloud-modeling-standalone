package org.activiti.cloud.modeling.standalone.auth.rest;

import java.util.UUID;

import org.activiti.cloud.modeling.standalone.auth.rest.model.Entry;
import org.activiti.cloud.modeling.standalone.auth.rest.model.TicketRequest;
import org.activiti.cloud.modeling.standalone.auth.rest.model.TicketResponse;
import org.activiti.cloud.modeling.standalone.auth.service.AuthProperty;
import org.activiti.cloud.modeling.standalone.auth.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alfresco/api/-default-/public/authentication/versions/1")
public class AuthenticationController {

	@Autowired
	private AuthProperty authProperty;

	@RequestMapping(path = "/tickets", method = RequestMethod.POST)
	public TicketResponse post(@RequestBody TicketRequest request) {
		if (!authProperty.isExists(request.getUserId(), request.getPassword())) {
			throw new UsernameNotFoundException("The user name or password you entered is incorrect.");
		}
		TicketResponse response = new TicketResponse();
		Entry entry = new Entry();
		entry.setId(UUID.randomUUID().toString());
		entry.setUserId(request.getUserId());
		TicketService.setTicket(entry);
		response.setEntry(entry);
		return response;

	}

	@RequestMapping(path = "/tickets/-me-", method = RequestMethod.DELETE)
	public void deleteTicketResponse(Authentication authentication) {
		Object id = authentication.getCredentials();
		if (id != null) {
			TicketService.deleteTicket((String) id);
		}
	}

	@RequestMapping(path = "/tickets/-me-", method = RequestMethod.GET)
	public TicketResponse validateTicketResponse(Authentication authentication) {
		Object id = authentication.getCredentials();
		if (id != null) {
			String userId = TicketService.getUserId((String) id);
			if (userId != null) {
				TicketResponse response = new TicketResponse();
				Entry entry = new Entry();
				entry.setId((String) id);
				response.setEntry(entry);
				return response;
			}
		}
		throw new RuntimeException("Invalid ticket");
	}
}