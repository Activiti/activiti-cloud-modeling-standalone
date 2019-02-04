package org.activiti.cloud.modeling.standalone.auth;

import org.activiti.cloud.modeling.standalone.auth.service.TicketAuthenticationUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> authenticationUserDetailsService() {
        return new TicketAuthenticationUserDetailsService();
    }

    @Bean
    public PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider() {
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(authenticationUserDetailsService());
        provider.setUserDetailsChecker(new AccountStatusUserDetailsChecker());
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(preAuthenticatedAuthenticationProvider());
    }

    @Bean
    public AbstractPreAuthenticatedProcessingFilter preAuthenticatedProcessingFilter() throws Exception {
        TicketAuthPreAuthenticatedProcessingFilter filter = new TicketAuthPreAuthenticatedProcessingFilter();
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(
                // Authentication endpoint
                "/alfresco/api/-default-/public/authentication/versions/1/tickets/-me-",
                "/alfresco/api/-default-/public/authentication/versions/1/tickets",
                // Logic page
                "/login",
                // Static resources for logic page
                "/**/*.json", "/**/*.css", "/**/*.png", "/**/*.js", "/**/*.svg").permitAll().anyRequest()
                .authenticated().and().addFilter(preAuthenticatedProcessingFilter()).exceptionHandling()
                .accessDeniedPage("/login").authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf()
                .disable();
    }
}