package com.sap.ro.plc.controller;

import com.sap.ro.plc.model.ProjectEntity;
import com.sap.ro.plc.service.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("projects")
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/one")
    public String getOne() {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setCreatedBy("petrut");
        projectEntity.setCreatedOn(Calendar.getInstance());
        projectEntity.setName("one");
        projectService.saveAll(Collections.singletonList(projectEntity));
        return "ok";
    }

    @GetMapping("/two")
    public String getTwo() {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setCreatedBy("petrut");
        projectEntity.setCreatedOn(Calendar.getInstance());
        projectEntity.setName("two");
        projectService.saveAll(Collections.singletonList(projectEntity));
        return "ok";
    }

    @GetMapping
    public List<ProjectEntity> get() {
        return projectService.findAll();
    }

    @PostMapping
    public List<ProjectEntity> put(@RequestBody List<ProjectEntity> projectEntityList) {
        return projectService.findAll();
    }
}
