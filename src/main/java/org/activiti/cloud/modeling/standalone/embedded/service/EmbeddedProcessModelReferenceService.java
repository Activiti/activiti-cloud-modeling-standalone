package org.activiti.cloud.modeling.standalone.embedded.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.activiti.cloud.organization.api.ModelValidationError;
import org.activiti.cloud.organization.core.rest.client.feign.ProcessModelReferenceService;
import org.activiti.cloud.organization.core.rest.client.model.ModelReference;
import org.activiti.cloud.services.process.model.core.model.ProcessModel;
import org.activiti.cloud.services.process.model.core.model.ProcessModelVersion;
import org.activiti.cloud.services.process.model.jpa.ProcessModelRepository;
import org.activiti.cloud.services.process.model.services.validate.ProcessModelValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("embedded")
public class EmbeddedProcessModelReferenceService implements ProcessModelReferenceService {

    @Autowired
    ProcessModelRepository processModelRepository;

    @Autowired
    ProcessModelValidatorService processModelValidatorService;

    @Override
    public void createResource(ModelReference model) {
        ProcessModel entity = new ProcessModel();
        entity.setModelId(model.getModelId());
        entity.setName(model.getName());
        entity.setContentType(model.getContentType());
        entity.setContent(model.getContent());
        entity.setExtensions(model.getExtensions());
        processModelRepository.save(entity);
    }

    @Override
    public void deleteResource(String modelId) {
        processModelRepository.deleteById(modelId);
    }

    @Override
    public ModelReference getResource(String modelId) {
        ProcessModel entity = processModelRepository.getOne(modelId);
        ProcessModelVersion versionEntity = entity.getLatestVersion();
        ModelReference model = new ModelReference();
        model.setModelId(entity.getModelId());
        model.setVersion(versionEntity.getVersion());
        model.setName(versionEntity.getName());
        model.setContent(versionEntity.getContent());
        model.setContentType(versionEntity.getContentType());
        model.setExtensions(versionEntity.getExtensions());
        return model;
    }

    @Override
    public void updateResource(String modelId, ModelReference model) {
        ProcessModel entity = processModelRepository.getOne(modelId);
        entity.setModelId(model.getModelId());
        entity.setName(model.getName());
        entity.setContentType(model.getContentType());
        entity.setContent(model.getContent());
        entity.setExtensions(model.getExtensions());
        processModelRepository.save(entity);
    }

    @Override
    public List<ModelValidationError> validateResourceContent(byte[] file) {
        List<ModelValidationError> results = new ArrayList<ModelValidationError>();
        try {
            processModelValidatorService.validate(file).stream().forEach(error -> {
                ModelValidationError result = new ModelValidationError();
                result.setValidatorSetName(error.getValidatorSetName());
                result.setProblem(error.getProblem());
                result.setDescription(error.getDescription());
                result.setWarning(error.isWarning());
                results.add(result);
            });
        } catch (XMLStreamException | IOException e) {
            throw new RuntimeException("TEST");
        }
        return results;
    }

}
