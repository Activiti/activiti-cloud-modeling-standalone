package org.activiti.cloud.modeling.standalone.auth;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class TicketAuthPreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		return "";
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if ("CSRF-TOKEN".equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}

}
