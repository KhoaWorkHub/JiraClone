package com.khoa.managementsystem.service.Impl;

import com.khoa.managementsystem.exception.BusinessException;
import com.khoa.managementsystem.exception.ProjectExceptionEnum;
import com.khoa.managementsystem.model.User;
import com.khoa.managementsystem.repository.UserRepository;
import com.khoa.managementsystem.security.JwtProvider;
import com.khoa.managementsystem.security.UserPrinciple;
import com.khoa.managementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getCurrentUser(UserPrinciple userPrinciple) {
        return userRepository.findById(userPrinciple.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userPrinciple.getId()));
    }


    @Override
    public User findUserProfileByJwt(String jwt) {
        String email = JwtProvider.getEmailFromToken(jwt);
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new BusinessException(ProjectExceptionEnum.USER_NOT_FOUND);
        }
        return user;
    }

    @Override
    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new BusinessException(ProjectExceptionEnum.USER_NOT_FOUND);
        }
        return user.get();
    }

    @Override
    public User updateUserProjectSize(User user, int size) {
        user.setProjectSize(user.getProjectSize() + size);

        return userRepository.save(user);
    }
}
