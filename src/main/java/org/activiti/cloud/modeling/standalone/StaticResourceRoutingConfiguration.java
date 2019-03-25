package org.activiti.cloud.modeling.standalone;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class StaticResourceRoutingConfiguration implements WebMvcConfigurer {

    @Autowired
    private ResourceProperties resourceProperties;

    @Value("${isUseExternalStaticResourceLocation:false}")
    private boolean isUseExternalStaticResourceLocation;

    @Value("${externalStaticResourceLocation:/opt/static}")
    private String externalStaticResourceLocation;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String[] resourceLocations = null;
        // Docker mode
        if (isUseExternalStaticResourceLocation) {
            resourceLocations = new String[]{externalStaticResourceLocation};
            // Spring Boot application mode 
        } else {
            resourceLocations = resourceProperties.getStaticLocations();
        }
        registry.addResourceHandler("/**")
                .addResourceLocations(resourceLocations)
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {

                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource resource = super.getResource(resourcePath, location);
                        if (resource != null) {
                            return resource;
                        } else {
                            return super.getResource("index.html", location);
                        }
                    }
                });
    }
}