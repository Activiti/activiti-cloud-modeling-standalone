package org.activiti.cloud.modeling.standalone.auth;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class TicketAuthPreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {

    private static final String TICKET_PREFIX = "ROLE_TICKET:";
    private static final String BASIC_AUTH_PREFIX = "Basic ";

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return "";
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        String value = request.getHeader("Authorization");
        if (value == null || !value.startsWith(BASIC_AUTH_PREFIX)) {
            return null;
        }
        byte[] bytes = Base64.getDecoder().decode(value.split(BASIC_AUTH_PREFIX)[1]);
        String decodeResult = new String(bytes, StandardCharsets.UTF_8);
        if (!decodeResult.startsWith(TICKET_PREFIX)) {
            return null;
        }
        return decodeResult.split(TICKET_PREFIX)[1];
    }

}
