package org.activiti.cloud.modeling.standalone.auth.service;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class TicketAuthenticationUserDetailsService
		implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
		Object credentials = token.getCredentials();
		if (credentials == null || credentials.toString() == "") {
			throw new UsernameNotFoundException("User not found.");
		}

		String userId = TicketService.getUserId((String) credentials);
		if (userId == null) {
			throw new UsernameNotFoundException("User not found.");
		}
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ACTIVITI_MODELER"));
		return new User(userId, (String) credentials, authorities);
	}

}
