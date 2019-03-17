package org.activiti.cloud.modeling.standalone.auth;

import org.activiti.api.runtime.shared.security.SecurityManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LocalSpringSecurityManager implements SecurityManager {

    @Override
    public String getAuthenticatedUserId() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return "";
        }

        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
