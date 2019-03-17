package org.activiti.cloud.modeling.standalone.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers(
                         // Authentication endpoint
                         "/**/app/authentication",
                         // Logic page
                         "/login",
                         // Static resources for logic page
                         "/**/*.json",
                         "/**/*.css",
                         "/**/*.png",
                         "/**/*.js",
                         "/**/*.svg")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .exceptionHandling()
            .accessDeniedPage("/login")
            .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
            .and()
            .csrf()
            .disable();

        http.formLogin()
            .loginProcessingUrl("/**/app/authentication")
            .loginPage("/login") // ログインページ
            .usernameParameter("j_username") // ユーザー名のパラメータ
            .passwordParameter("j_password") // パスワードのパラメータ
            .defaultSuccessUrl("/dashboard/projects") // 認証成功時の遷移先
            .and();

        http.logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/**/app/logout"))
            .logoutSuccessUrl("/login");
    }

    @Configuration
    protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

        @Autowired
        private LocalAuthenticationProvider authenticationProvider;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authenticationProvider);
        }
    }
}