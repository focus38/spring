package ru.focus.spring.auth.resource.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.focus.spring.auth.resource.model.ProjectDto;
import ru.focus.spring.auth.resource.service.ProjectService;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static ru.focus.spring.auth.resource.service.ProjectStore.PROJECT_LIST;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Override
    public List<ProjectDto> getList() {
        return PROJECT_LIST.stream()
            .filter(filterByOwner())
            .toList();
    }

    @Override
    public ProjectDto getById(final String id) {
        return PROJECT_LIST.stream()
            .filter(p -> p.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Проект не найден"));
    }

    private Predicate<ProjectDto> filterByOwner() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated()) {
            return p -> Objects.isNull(p.getOwnerName());
        }
        final String userName = auth.getName();
        return p -> Objects.nonNull(p.getOwnerName())
                    && p.getOwnerName().equals(userName);
    }
}
