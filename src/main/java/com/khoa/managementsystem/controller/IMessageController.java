package com.khoa.managementsystem.controller;

import com.khoa.managementsystem.request.CreateMessageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/message")
public interface IMessageController {

    @PostMapping("/send")
    ResponseEntity<Object> sendMessage(@RequestBody CreateMessageRequest request,
                                       @RequestHeader("Authorization") String jwt
    );

    @GetMapping("/chat/{projectId}")
    ResponseEntity<Object> getMessagesByChatId(@PathVariable Long projectId
    );
}
