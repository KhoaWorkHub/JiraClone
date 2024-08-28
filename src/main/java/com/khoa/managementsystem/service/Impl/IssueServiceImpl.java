package com.khoa.managementsystem.service.Impl;

import com.khoa.managementsystem.exception.BusinessException;
import com.khoa.managementsystem.exception.ProjectExceptionEnum;
import com.khoa.managementsystem.model.Issue;
import com.khoa.managementsystem.model.Project;
import com.khoa.managementsystem.model.User;
import com.khoa.managementsystem.repository.IssueRepository;
import com.khoa.managementsystem.request.IssueRequest;
import com.khoa.managementsystem.service.IssueService;
import com.khoa.managementsystem.service.ProjectService;
import com.khoa.managementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;

    private final ProjectService projectService;

    private final UserService userService;

    @Override
    public Issue getIssueById(Long issueId) {
        Optional<Issue> issue = issueRepository.findById(issueId);
        if (!issue.isPresent()) {
            throw new BusinessException(ProjectExceptionEnum.ISSUE_NOT_FOUND);
        }
        return issue.get();
    }

    @Override
    public List<Issue> getIssuesByProjectId(Long projectId) {
        return issueRepository.findByProjectID(projectId);
    }

    @Override
    public Issue createIssue(IssueRequest issueRequest, User user) {
        Project project = projectService.getProjectById(issueRequest.getProjectId());

        Issue issue = new Issue();
        issue.setTitle(issueRequest.getTitle());
        issue.setDescription(issueRequest.getDescription());
        issue.setStatus(issueRequest.getStatus());
        issue.setProjectID(issueRequest.getProjectId());
        issue.setPriority(issueRequest.getPriority());
        issue.setDueDate(issueRequest.getDueDate());

        issue.setProject(project);

        return issueRepository.save(issue);
    }

    @Override
    public void deleteIssue(Long issueId, Long userId) {
        getIssueById(issueId);
        issueRepository.deleteById(issueId);
    }

    @Override
    public Issue addUserToIssue(Long issueId, Long userId) {
        User user = userService.findUserById(userId);
        Issue issue = getIssueById(issueId);

        issue.setAssignee(user);
        return issueRepository.save(issue);
    }

    @Override
    public Issue updateStatus(Long issueId, String status) {
        Issue issue = getIssueById(issueId);

        issue.setStatus(status);
        return issueRepository.save(issue);
    }
}
