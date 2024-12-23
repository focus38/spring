package ru.focus.spring.auth.resource.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.focus.spring.auth.resource.model.ProjectDto;
import ru.focus.spring.auth.resource.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public List<ProjectDto> getList() {
        return projectService.getList();
    }

    @GetMapping("/{id}")
    public ProjectDto getById(@PathVariable("id") final String id) {
        return projectService.getById(id);
    }
}
