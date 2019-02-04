package org.activiti.cloud.modeling.standalone.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:/auth.properties")
public class AuthProperty {
    @Autowired
    private Environment env;

    public boolean isExists(String userId, String password) {
        String value = env.getProperty(userId);
        return ((value != null) && (value.equals(password)));
    }
}