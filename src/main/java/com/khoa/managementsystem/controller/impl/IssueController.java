package com.khoa.managementsystem.controller.impl;


import com.khoa.managementsystem.controller.IIssueController;
import com.khoa.managementsystem.exception.BusinessException;
import com.khoa.managementsystem.exception.ProjectExceptionEnum;
import com.khoa.managementsystem.model.Issue;
import com.khoa.managementsystem.model.User;
import com.khoa.managementsystem.request.IssueRequest;
import com.khoa.managementsystem.response.AuthResponse;
import com.khoa.managementsystem.response.IssueResponse;
import com.khoa.managementsystem.response.MessageResponse;
import com.khoa.managementsystem.service.IssueService;
import com.khoa.managementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IssueController extends BaseController implements IIssueController {

    private final IssueService issueService;

    private final UserService userService;

    @Override
    public ResponseEntity<Object> getIssueById(Long issueId) {
        return ok(issueService.getIssueById(issueId));
    }

    @Override
    public ResponseEntity<Object> getIssueByProjectId(Long projectId) {
        return ok(issueService.getIssuesByProjectId(projectId));
    }

    @Override
    public ResponseEntity<Object> createIssue(IssueRequest issueRequest, String jwt) {
        User userToken = userService.findUserProfileByJwt(jwt);
        User user = userService.findUserById(userToken.getId());

        if(user != null) {

            Issue createdIssue = issueService.createIssue(issueRequest, userToken);
            IssueResponse issueResponse = new IssueResponse();

            issueResponse.setDescription(createdIssue.getDescription());
            issueResponse.setDueDate(createdIssue.getDueDate());
            issueResponse.setPriority(createdIssue.getPriority());
            issueResponse.setId(createdIssue.getId());
            issueResponse.setStatus(createdIssue.getStatus());
            issueResponse.setTitle(createdIssue.getTitle());
            issueResponse.setProjectID(createdIssue.getProjectID());
            issueResponse.setProject(createdIssue.getProject());
            issueResponse.setTags(createdIssue.getTags());
            issueResponse.setAssigned(createdIssue.getAssigned());

            return ok(issueResponse);

        }
        throw new BusinessException(ProjectExceptionEnum.USER_NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> deleteIssue(Long issueId, String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
        issueService.deleteIssue(issueId, user.getId());

        MessageResponse res = new MessageResponse();
        res.setMessage("Issue deleted successfully");

        return ok(res);
    }

    @Override
    public ResponseEntity<Object> addUserToIssue(Long issueId, Long userId) {
        Issue issue = issueService.addUserToIssue(issueId, userId);
        return ok(issue);
    }

    @Override
    public ResponseEntity<Object> updateStatus(Long issueId, String status) {
        Issue issue = issueService.updateStatus(issueId, status);
        return ok(issue);
    }
}
