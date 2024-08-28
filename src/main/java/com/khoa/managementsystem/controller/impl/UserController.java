package com.khoa.managementsystem.controller.impl;

import com.khoa.managementsystem.controller.IUserController;
import com.khoa.managementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController extends BaseController implements IUserController {

    private final UserService userService;

    @Override
    public ResponseEntity<Object> getUserProfile(String jwt) {
        return ok(userService.findUserProfileByJwt(jwt));
    }
}