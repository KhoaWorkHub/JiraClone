package com.khoa.managementsystem.controller.impl;

import com.khoa.managementsystem.controller.IProjectController;
import com.khoa.managementsystem.model.Invitation;
import com.khoa.managementsystem.model.Project;
import com.khoa.managementsystem.model.User;
import com.khoa.managementsystem.request.InvitationRequest;
import com.khoa.managementsystem.response.MessageResponse;
import com.khoa.managementsystem.security.UserPrinciple;
import com.khoa.managementsystem.service.InvitationService;
import com.khoa.managementsystem.service.ProjectService;
import com.khoa.managementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ProjectController extends BaseController implements IProjectController {

    private final ProjectService projectService;

    private final UserService userService;

    private final InvitationService invitationService;

    @Override
    public ResponseEntity<Object> getProject( String category, String tag, String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
//        List<Project> projects = projectService.getProjectByTeam(category, tag, user);
//        return new ResponseEntity<>(projects, HttpStatus.OK);
        return ok(projectService.getProjectByTeam(category, tag, user));
    }

    @Override
    public ResponseEntity<Object> getProjectId(Long projectId, String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
        return ok(projectService.getProjectById(projectId));
    }

    @Override
    public ResponseEntity<Object> createProject(Project project, Long projectId, String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
        Project createdProject = projectService.createProject(project, user);
        return ResponseEntity.ok(createdProject);
    }

    @Override
    public ResponseEntity<Object> updateProject(Long projectId, Project project, String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
        return ok(projectService.updateProject(project, projectId));
    }

    @Override
    public ResponseEntity<Object> deleteProject(Long projectId, String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId, user.getId());
        MessageResponse res = new MessageResponse("Project deleted successfully");
        return ok(res); //khúc này tạo thêm msg res để ko cần return trong method void delete
        //thg method delete có thể dug kiểu boolean trả về true, false cug dc nhưng thg này dễ hiểu hơn
    }

    @Override
    public ResponseEntity<Object> searchProject(String keyword, String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
        return ok(projectService.searchProject(keyword, user));
    }

    @Override
    public ResponseEntity<Object> getChatByProjectId(Long projectId, String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
        return ok(projectService.getChatByProjectId(projectId));
    }

    @Override
    public ResponseEntity<Object> inviteProject(InvitationRequest request, String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
        invitationService.sendInvitation(request.getEmail(), request.getProjectId());
        MessageResponse res = new MessageResponse("Invitation sent successfully");
        return ok(res);
    }

    @Override
    public ResponseEntity<Object> acceptInviteProject(String token, String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
        Invitation invitation = invitationService.acceptInvitation(token, user.getId());
        projectService.addUserToProject(invitation.getProjectId(), user.getId());
        return ok(invitation);
    }
}
