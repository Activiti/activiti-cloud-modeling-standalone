package org.activiti.cloud.modeling.standalone.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:/auth.properties")
public class AuthProperty {

    @Autowired
    private Environment env;

    @Value("${activiti.modeling.standalone.default.user.id}")
    private String defaultUserId;

    @Value("${activiti.modeling.standalone.default.user.password}")
    private String defaultUserPassword;

    public boolean isExists(String userId, String password) {
        if (defaultUserId.equals(userId) && defaultUserPassword.equals(password))
            return true;

        String value = env.getProperty(userId);
        return ((value != null) && (value.equals(password)));
    }
}