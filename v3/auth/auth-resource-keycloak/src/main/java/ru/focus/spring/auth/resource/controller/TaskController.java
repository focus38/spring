package ru.focus.spring.auth.resource.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    @GetMapping
    public List<String> getTasks() {
        return List.of("Task №1");
    }
}
