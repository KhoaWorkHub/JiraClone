package com.khoa.managementsystem.controller;


import com.khoa.managementsystem.request.IssueRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/issue")
public interface IIssueController {

    @GetMapping("/{issueId}")
    ResponseEntity<Object> getIssueById(@PathVariable Long issueId);

    @GetMapping("/project/{projectId}")
    ResponseEntity<Object> getIssueByProjectId(@PathVariable Long projectId);
//    ResponseEntity<List<Issue>> getIssueByProjectId(@PathVariable Long projectId);

    @PostMapping("/create")
    ResponseEntity<Object> createIssue(@RequestBody IssueRequest issueRequest,
                                       @RequestHeader("Authorization") String jwt
    );

    @DeleteMapping("/{issueId}")
    ResponseEntity<Object> deleteIssue(@PathVariable Long issueId,
                                       @RequestHeader("Authorization") String jwt

    );

    @PutMapping("/{issueId}/assigned/{userId}")
    ResponseEntity<Object> addUserToIssue(@PathVariable Long issueId,
                                          @PathVariable Long userId
//                                          @RequestHeader("Authorization") String jwt
    );

    @PutMapping("/{issueId}/status/{status}")
    ResponseEntity<Object> updateStatus(@PathVariable Long issueId,
                                        @PathVariable String status
    );
}
