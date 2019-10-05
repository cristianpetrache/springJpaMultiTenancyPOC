package com.sap.ro.plc.service;

import com.sap.ro.plc.model.ProjectEntity;
import com.sap.ro.plc.repository.ProjectRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Optional<ProjectEntity> findById(UUID id) {
        return projectRepository.findById(id);
    }

    public List<ProjectEntity> findAll() {
        return projectRepository.findAll();
    }

    public List<ProjectEntity> saveAll(List<ProjectEntity> projectEntityList) {
        return projectRepository.saveAll(projectEntityList);
    }
}
