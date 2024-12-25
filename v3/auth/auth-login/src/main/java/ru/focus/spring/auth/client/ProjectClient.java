package ru.focus.spring.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.focus.spring.auth.config.ProjectClientConfiguration;
import ru.focus.spring.auth.model.ProjectDto;

import java.util.List;

@FeignClient(name = "project-client", url = "${service.project.baseurl}", configuration = ProjectClientConfiguration.class)
public interface ProjectClient {

    @RequestMapping(path = "/api/v1/project", method = RequestMethod.GET)
    List<ProjectDto> getProjectList();

    @RequestMapping(path = "/api/v1/project/{id}", method = RequestMethod.GET)
    ProjectDto getProjectById(@PathVariable("id") final String id);

    @RequestMapping(path = "/api/v1/task", method = RequestMethod.GET)
    List<String> getTaskList();
}
