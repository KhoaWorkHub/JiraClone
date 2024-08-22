package com.khoa.managementsystem.service;

import com.khoa.managementsystem.model.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(Long issueId, Long userId, String content);

    void deleteComment(Long commentId, Long userId);

    List<Comment> findCommentByIssueId(Long issueId);
}
