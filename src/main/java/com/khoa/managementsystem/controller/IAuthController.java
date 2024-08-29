package com.khoa.managementsystem.controller;

import com.khoa.managementsystem.model.User;
import com.khoa.managementsystem.request.LoginRequest;
import com.khoa.managementsystem.response.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public interface IAuthController {

    //Todo: register
    @PostMapping("/signup")
    ResponseEntity<Object> createUserHandler(@RequestBody User user);
    //Dùng user ko dug custom basecontroller <Object> vì nó flex như type any js

    @PostMapping("/login")
    ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest);

}
