package com.khoa.managementsystem.service;


import com.khoa.managementsystem.model.Chat;
import com.khoa.managementsystem.model.Project;
import com.khoa.managementsystem.model.User;

import java.util.List;

public interface ProjectService {

    Project createProject(Project project, User user);

    List<Project> getProjectByTeam( String category, String tag, User user);

    Project getProjectById(Long projectId);

    void deleteProject(Long projectId, Long userId);

    void addUserToProject(Long projectId, Long userId);

    void removeUserFromProject(Long projectId, Long userId);

    Project updateProject(Project updateProject, Long projectId);

    Chat getChatByProjectId(Long projectId);

    List<Project> searchProject(String keyword, User user);
}
