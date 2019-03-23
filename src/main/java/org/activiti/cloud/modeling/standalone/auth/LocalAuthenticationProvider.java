package org.activiti.cloud.modeling.standalone.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.activiti.cloud.modeling.standalone.auth.service.AuthProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class LocalAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AuthProperty authProperty;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (!authProperty.isExists(username, password)) {
            throw new UsernameNotFoundException("The user name or password you entered is incorrect.");
        }

        Collection<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_ACTIVITI_MODELER"));
        User user = new User(username, password, authorityList);
        return new UsernamePasswordAuthenticationToken(user, password, authorityList);
    }

    @Override
    public boolean supports(Class<?> token) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(token);
    }
}
