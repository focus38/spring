package ru.focus.spring.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.focus.spring.auth.client.ProjectClient;
import ru.focus.spring.auth.model.ProjectDto;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProjectClient projectClient;


    @GetMapping(path = {"/", "/home"})
    public String home(final Model model, @AuthenticationPrincipal final OAuth2User principal) {
        model.addAttribute("is_authenticated", true);
        model.addAttribute("user_name", principal.getName());
        return "home";
    }

    @GetMapping("/home/project")
    public String getProjects(final Model model) {
        final List<ProjectDto> projects = projectClient.getProjectList();
        model.addAttribute("projects", projects);
        return "projects";
    }

    @GetMapping("/home/task")
    public String getTasks(final Model model) {
        final List<String> tasks = projectClient.getTaskList();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }
}
