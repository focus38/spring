package ru.focus.spring.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.focus.spring.auth.config.ProjectClientConfiguration;
import ru.focus.spring.auth.model.ProjectDto;

import java.util.List;

@FeignClient(name = "project-client", url = "http://localhost:8091", configuration = ProjectClientConfiguration.class)
public interface ProjectClient {

    @RequestMapping(path = "/api/v1/project")
    List<ProjectDto> getList();

    @RequestMapping(path = "/api/v1/project/{id}")
    ProjectDto getById(@PathVariable("id") final String id);
}
