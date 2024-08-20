package com.khoa.managementsystem.controller;

import com.khoa.managementsystem.model.Project;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RequestMapping("api/project")
public interface IProjectController {

    @GetMapping
    ResponseEntity<Object> getProject(@RequestParam(required = false) String category,
                                      @RequestParam(required = false) String tag,
                                      @RequestHeader("Authorization") String jwt
    );

    @GetMapping("/{projectId}")
    ResponseEntity<Object> getProjectId(@PathVariable Long projectId,
                                        @RequestHeader("Authorization") String jwt
    );

    @PostMapping("/create")
    ResponseEntity<Object> createProject(@RequestBody Project project,
                                         @PathVariable Long projectId,
                                         @RequestHeader("Authorization") String jwt
    );

    @PatchMapping("/{projectId}")
    ResponseEntity<Object> updateProject(@PathVariable Long projectId,
                                         @RequestBody Project project,
                                         @RequestHeader("Authorization") String jwt
    );

    @DeleteMapping("/{projectId}")
    ResponseEntity<Object> deleteProject(@PathVariable Long projectId,
                                         @RequestHeader("Authorization") String jwt
    );

    @GetMapping("/search")
    ResponseEntity<Object> searchProject(@RequestParam(required = false) String keyword,
                                         @RequestHeader("Authorization") String jwt
    );

    @GetMapping("/{projectId}/chat")
    ResponseEntity<Object> getChatByProjectId(@PathVariable Long projectId,
                                              @RequestHeader("Authorization") String jwt
    );
}
