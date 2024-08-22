package com.khoa.managementsystem.controller.impl;

import com.khoa.managementsystem.controller.ICommentController;
import com.khoa.managementsystem.model.Comment;
import com.khoa.managementsystem.model.User;
import com.khoa.managementsystem.request.CreateCommentRequest;
import com.khoa.managementsystem.response.MessageResponse;
import com.khoa.managementsystem.service.CommentService;
import com.khoa.managementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController extends BaseController implements ICommentController {

    private final CommentService commentService;

    private final UserService userService;

    @Override
    public ResponseEntity<Object> createComment(CreateCommentRequest request, String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
        return ok(commentService.createComment(
                request.getIssueId(),
                user.getId(),
                request.getContent()));
    }

    @Override
    public ResponseEntity<Object> deleteComment(Long commentId, String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
        commentService.deleteComment(commentId, user.getId());
        MessageResponse res = new MessageResponse("Comment deleted successfully");
        return ok(res);
    }

    @Override
    public ResponseEntity<Object> getCommentByIssueId(Long issueId) {
        return ok(commentService.findCommentByIssueId(issueId));
    }
}
