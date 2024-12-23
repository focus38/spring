package ru.focus.spring.auth.resource.service;

import ru.focus.spring.auth.resource.model.ProjectDto;

import java.util.List;

/**
 * Сервис для работы с проектами.
 */
public interface ProjectService {

    /**
     * Запрашивает доступный пользователю список проектов.
     *
     * @return список проектов.
     */
    List<ProjectDto> getList();

    /**
     * Запрашивает проект по идентификатору.
     *
     * @param id идентификатор проекта.
     * @return проект по идентификатору.
     */
    ProjectDto getById(final String id);
}
