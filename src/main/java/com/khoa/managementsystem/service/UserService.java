package com.khoa.managementsystem.service;

import com.khoa.managementsystem.model.User;
import com.khoa.managementsystem.security.UserPrinciple;

public interface UserService {

    User getCurrentUser(UserPrinciple userPrinciple);

    User findUserProfileByJwt(String jwt);

    User findUserByEmail(String email);

    User findUserById(Long id);

    User updateUserProjectSize(User user, int size);
}
