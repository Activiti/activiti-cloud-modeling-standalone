package org.activiti.cloud.modeling.standalone.embedded.service;

import java.util.List;

import org.activiti.cloud.organization.api.ModelValidationError;
import org.activiti.cloud.organization.core.rest.client.feign.ProcessModelReferenceService;
import org.activiti.cloud.organization.core.rest.client.model.ModelReference;
import org.activiti.cloud.organization.core.rest.client.service.ModelReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class EmbeddedModelReferenceService implements ModelReferenceService {

    @Autowired
    @Qualifier("embedded")
    ProcessModelReferenceService processModelReferenceService;

    @Override
    public void createResource(String modelType, ModelReference model) {
        processModelReferenceService.createResource(model);
    }

    @Override
    public void deleteResource(String modelType, String id) {
        processModelReferenceService.deleteResource(id);
    }

    @Override
    public ModelReference getResource(String modelType, String modelId) {
        return processModelReferenceService.getResource(modelId);
    }

    @Override
    public void updateResource(String modelType, String id, ModelReference model) {
        processModelReferenceService.updateResource(id, model);
    }

    @Override
    public List<ModelValidationError> validateResourceContent(String modelType, byte[] file) {
        return processModelReferenceService.validateResourceContent(file);
    }

}
