package com.khoa.managementsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/user")
public interface IUserController {

    @GetMapping("/profile")
    ResponseEntity<Object> getUserProfile(@RequestHeader("Authorization") String jwt);
}