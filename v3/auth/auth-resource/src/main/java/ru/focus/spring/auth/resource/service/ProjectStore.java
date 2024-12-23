package ru.focus.spring.auth.resource.service;

import ru.focus.spring.auth.resource.model.ProjectDto;

import java.util.List;

public abstract class ProjectStore {

    public static final List<ProjectDto> PROJECT_LIST = List.of(
        createProject("00000000-0000-0000-0000-000000000001", "Первый", "alex"),
        createProject("00000000-0000-0000-0000-000000000002", "Второй", "max"),
        createProject("00000000-0000-0000-0000-000000000003", "Третий", "alex"),
        createProject("00000000-0000-0000-0000-000000000004", "Третий", "max"),
        createProject("00000000-0000-0000-0000-000000000005", "Пятый", "alex"),
        createProject("00000000-0000-0000-0000-000000000006", "Шестой", "anna"),
        createProject("00000000-0000-0000-0000-000000000007", "Седьмой", null)
    );

    private static ProjectDto createProject(final String id, final String name, final String ownerName) {
        return ProjectDto.builder()
            .id(id)
            .name(name)
            .ownerName(ownerName)
            .build();
    }
}
