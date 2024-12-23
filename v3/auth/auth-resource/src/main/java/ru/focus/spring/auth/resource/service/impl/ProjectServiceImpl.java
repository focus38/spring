package ru.focus.spring.auth.resource.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.focus.spring.auth.resource.model.ProjectDto;
import ru.focus.spring.auth.resource.service.ProjectService;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Service
public class ProjectServiceImpl implements ProjectService {

    private static final List<ProjectDto> projects = List.of(
        createProject("00000000-0000-0000-0000-000000000001", "Первый", "alex"),
        createProject("00000000-0000-0000-0000-000000000002", "Второй", "max"),
        createProject("00000000-0000-0000-0000-000000000003", "Третий", "alex"),
        createProject("00000000-0000-0000-0000-000000000004", "Третий", "max"),
        createProject("00000000-0000-0000-0000-000000000005", "Пятый", "alex"),
        createProject("00000000-0000-0000-0000-000000000006", "Шестой", "anna"),
        createProject("00000000-0000-0000-0000-000000000007", "Седьмой", null)
    );

    @Override
    public List<ProjectDto> getList() {
        return projects.stream()
            .filter(filterByOwner())
            .toList();
    }

    @Override
    public ProjectDto getById(final String id) {
        return projects.stream()
            .filter(p -> p.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Проект не найден"));
    }

    private static ProjectDto createProject(final String id, final String name, final String ownerName) {
        return ProjectDto.builder()
            .id(id)
            .name(name)
            .ownerName(ownerName)
            .build();
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
