package com.sap.ro.plc.controller;

import com.sap.ro.plc.model.ProjectEntity;
import com.sap.ro.plc.service.AsyncTestService;
import com.sap.ro.plc.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("projects")
public class ProjectController {

    private static final int NO_OF_ITERATIONS = 5;
    private Logger logger = LoggerFactory.getLogger(ProjectController.class);
    private ProjectService projectService;
    private AsyncTestService asyncTestService;

    public ProjectController(ProjectService projectService, AsyncTestService asyncTestService) {
        this.projectService = projectService;
        this.asyncTestService = asyncTestService;
    }

    @GetMapping("/async")
    public String getAsync() throws Exception {
        logger.info("async current thread: {}", Thread.currentThread());
        asyncTestService.threadName(logger);
        Set<Future<String>> futureSet = new HashSet<>();
        for (int i = 0; i++ < NO_OF_ITERATIONS; ) {
            futureSet.add(asyncTestService.async1(logger));
            futureSet.add(asyncTestService.async2(logger));
            futureSet.add(asyncTestService.async3(logger));
        }
        while (!futureSet.isEmpty()) {
            Iterator<Future<String>> futureSetIterator = futureSet.iterator();
            while (futureSetIterator.hasNext()) {
                Future<String> stringFuture = futureSetIterator.next();
                if (stringFuture.isDone()) {
                    try {
                        logger.info("Future is done: {}", stringFuture.get());
                    } catch (ExecutionException executionException) {
                        logger.error("Future exception", executionException.getCause());
                    }
                    futureSetIterator.remove();
                }
            }
            Thread.sleep(100);
        }
        return Thread.currentThread().getName();
    }

    @GetMapping("/sync")
    public String getSync() {
        logger.info("sync current thread: {}", Thread.currentThread());
        return Thread.currentThread().getName();
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

