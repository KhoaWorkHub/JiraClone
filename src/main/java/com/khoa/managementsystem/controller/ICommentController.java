package com.khoa.managementsystem.controller;

import com.khoa.managementsystem.request.CreateCommentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/comment")
public interface ICommentController {

    @PostMapping("/create")
    ResponseEntity<Object> createComment(@RequestBody CreateCommentRequest request,
                                         @RequestHeader("Authorization") String jwt
    );

    @DeleteMapping("/{commentId}")
    ResponseEntity<Object> deleteComment(@PathVariable Long commentId,
                                         @RequestHeader("Authorization") String jwt
    );

    @GetMapping("/{issueId}")
    ResponseEntity<Object> getCommentByIssueId(@PathVariable Long issueId);
}
