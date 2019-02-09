package org.activiti.cloud.modeling.standalone;

import org.activiti.cloud.organization.EnableActivitiOrganization;
import org.activiti.cloud.services.organization.jpa.version.ExtendedJpaRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = { "org.activiti.cloud.services.organization.jpa",
		"org.activiti.cloud.services.process.model.jpa" }, repositoryFactoryBeanClass = ExtendedJpaRepositoryFactoryBean.class)
@EntityScan(basePackages = { "org.activiti.cloud.services.process.model.core.model",
		"org.activiti.cloud.services.organization.entity" })

// We need to definitely remove this
@ComponentScan("org.activiti.cloud")
@EnableActivitiOrganization
public class ActivitiCloudModelingStandaloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivitiCloudModelingStandaloneApplication.class, args);
	}
}