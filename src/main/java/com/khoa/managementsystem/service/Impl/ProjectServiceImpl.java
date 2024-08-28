package com.khoa.managementsystem.service.Impl;

import com.khoa.managementsystem.exception.BusinessException;
import com.khoa.managementsystem.exception.ProjectExceptionEnum;
import com.khoa.managementsystem.model.Chat;
import com.khoa.managementsystem.model.Project;
import com.khoa.managementsystem.model.User;
import com.khoa.managementsystem.repository.ProjectRepository;
import com.khoa.managementsystem.service.ChatService;
import com.khoa.managementsystem.service.ProjectService;
import com.khoa.managementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {


    private final ProjectRepository projectRepository;


    private final UserService userService;


    private final ChatService chatService;

    @Override
    public Project createProject(Project project, User user) {
        Project createdProject = new Project();

        createdProject.setOwner(user);
        createdProject.setTags(project.getTags());
        createdProject.setName(project.getName());
        createdProject.setCategory(project.getCategory());
        createdProject.setDescription(project.getDescription());
        createdProject.getTeam().add(user);

        createdProject = projectRepository.save(createdProject);

        Chat chat = new Chat();
        chat.setProject(createdProject);
        chatService.createChat(chat);
        createdProject.setChat(chat);

        return projectRepository.save(createdProject);
    }


@Override
public List<Project> getProjectByTeam(String category, String tag, User user) {
    List<Project> projects = projectRepository.findByTeamContainingOrOwner(user, user);

    if(category != null){
        projects = projects.stream().filter(project ->
                project.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    if(tag != null){
        projects = projects.stream().filter(project ->
                        project.getTags().contains(tag))
                .collect(Collectors.toList());
    }
    return projects;
}

    @Override
    public Project getProjectById(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);

        if(project.isEmpty()){
            throw new BusinessException(ProjectExceptionEnum.PROJECT_NOT_FOUND);
        }
        return project.get();
    }

    @Override
    public void deleteProject(Long projectId, Long userId) {

        getProjectById(projectId);
        projectRepository.deleteById(projectId);

    }

    @Override
    public void addUserToProject(Long projectId, Long userId) {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);
        if(!project.getTeam().contains(user)){
            project.getChat().getUsers().add(user);
            project.getTeam().add(user);

        }
        projectRepository.save(project);

    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);
        if(project.getTeam().contains(user)){
            project.getChat().getUsers().add(user);
            project.getTeam().remove(user);

        }
        projectRepository.save(project);

    }

    @Override
    public Project updateProject(Project updateProject, Long projectId) {
        Project project = getProjectById(projectId);

        project.setName(updateProject.getName());
        project.setDescription(updateProject.getDescription());
        project.setTags(updateProject.getTags());

        projectRepository.save(project);
        return project;
    }

    @Override
    public Chat getChatByProjectId(Long projectId) {
        Project project = getProjectById(projectId);


        return project.getChat();
    }

    @Override
    public List<Project> searchProject(String keyword, User user) {

        List<Project> project = projectRepository.findByNameContainingAndTeamContains(keyword, user);

        return project;
    }
}
