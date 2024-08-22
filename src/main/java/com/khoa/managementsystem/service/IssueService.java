package com.khoa.managementsystem.service;

import com.khoa.managementsystem.model.Issue;
import com.khoa.managementsystem.model.User;
import com.khoa.managementsystem.request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    Issue getIssueById(Long id);

    List<Issue> getIssuesByProjectId(Long projectId);

    Issue createIssue(IssueRequest issueRequest, User user);

    void deleteIssue(Long issueId, Long userId);

    Issue addUserToIssue(Long issueId, Long userId);

    Issue updateStatus(Long issueId, String status);
}
