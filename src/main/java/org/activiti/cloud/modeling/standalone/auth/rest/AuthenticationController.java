package org.activiti.cloud.modeling.standalone.auth.rest;

import java.util.Map;

import org.activiti.cloud.modeling.standalone.auth.rest.model.Entry;
import org.activiti.cloud.modeling.standalone.auth.rest.model.TicketResponse;
import org.activiti.cloud.modeling.standalone.auth.service.AuthProperty;
import org.activiti.cloud.modeling.standalone.auth.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activiti-app/app/authentication")
public class AuthenticationController {

	@Autowired
	private AuthProperty authProperty;

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public TicketResponse post(@RequestParam Map<String, String> request,
			@CookieValue(value = "CSRF-TOKEN", required = true) String token) {
		if (!authProperty.isExists(request.get("j_username"), request.get("j_password"))) {
			throw new UsernameNotFoundException("The user name or password you entered is incorrect.");
		}
		TicketResponse response = new TicketResponse();
		Entry entry = new Entry();
		entry.setId(token);
		entry.setUserId(request.get("j_username"));
		TicketService.setTicket(entry);
		response.setEntry(entry);
		return response;

	}
}