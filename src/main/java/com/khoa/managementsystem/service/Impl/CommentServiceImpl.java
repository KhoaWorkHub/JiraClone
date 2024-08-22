package com.khoa.managementsystem.service.Impl;

import com.khoa.managementsystem.exception.BusinessException;
import com.khoa.managementsystem.exception.ProjectExceptionEnum;
import com.khoa.managementsystem.model.Comment;
import com.khoa.managementsystem.model.Issue;
import com.khoa.managementsystem.model.User;
import com.khoa.managementsystem.repository.CommentRepository;
import com.khoa.managementsystem.repository.IssueRepository;
import com.khoa.managementsystem.repository.UserRepository;
import com.khoa.managementsystem.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final IssueRepository issueRepository;

    private final UserRepository userRepository;

    @Override
    public Comment createComment(Long issueId, Long userId, String content) {
        Optional<Issue> issueOptional = issueRepository.findById(issueId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (issueOptional.isEmpty()){
            throw new BusinessException(ProjectExceptionEnum.valueOf(ProjectExceptionEnum.ISSUE_NOT_FOUND_WITH_ID.formatMessage(issueId)));
        }

        if(userOptional.isEmpty()){
            throw new BusinessException(ProjectExceptionEnum.valueOf(ProjectExceptionEnum.USER_NOT_FOUND_WITH_ID.formatMessage(userId)));
        }

        Issue issue = issueOptional.get();
        User user = userOptional.get();

        Comment comment = new Comment();

        comment.setIssue(issue);
        comment.setUser(user);
        comment.setCreatedDatetime(LocalDateTime.now());
        comment.setContent(content);

        Comment savedComment = commentRepository.save(comment);
        issue.getComments().add(savedComment);

        return savedComment;

    }

    @Override
    public void deleteComment(Long commentId, Long userId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        Optional<User> userOptional = userRepository.findById(userId);

        if(commentOptional.isEmpty()){
            throw new BusinessException(ProjectExceptionEnum.valueOf(ProjectExceptionEnum.COMMENT_NOT_FOUND_WITH_ID.formatMessage(commentId)));
        }

        if(userOptional.isEmpty()){
            throw new BusinessException(ProjectExceptionEnum.valueOf(ProjectExceptionEnum.USER_NOT_FOUND_WITH_ID.formatMessage(userId)));
        }

        Comment comment = commentOptional.get();
        User user = userOptional.get();

        if(!comment.getUser().equals(user)){
            throw new BusinessException(ProjectExceptionEnum.COMMENT_NOT_BELONG_TO_USER);
        }

        commentRepository.delete(comment);

    }

    @Override
    public List<Comment> findCommentByIssueId(Long issueId) {
        return commentRepository.findByIssueId(issueId);
    }
}
