package com.khoa.managementsystem.controller.impl;

import com.khoa.managementsystem.controller.IAuthController;
import com.khoa.managementsystem.exception.BusinessException;
import com.khoa.managementsystem.exception.ProjectExceptionEnum;
import com.khoa.managementsystem.model.User;
import com.khoa.managementsystem.repository.UserRepository;
import com.khoa.managementsystem.request.LoginRequest;
import com.khoa.managementsystem.response.AuthResponse;
import com.khoa.managementsystem.security.CustomUserDetailsImpl;
import com.khoa.managementsystem.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController extends BaseController implements IAuthController {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final CustomUserDetailsImpl customUserDetails;

    @Override
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) {

        User isUserExisted = userRepository.findByEmail(user.getEmail());
        if (isUserExisted != null) {
            throw new BusinessException(ProjectExceptionEnum.USER_ALREADY_EXISTED);
        }

        User createdUser = new User();
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());

        User savedUser = userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse res = new AuthResponse();
        res.setMessage("User registered successfully");
        res.setJwt(jwt);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<AuthResponse> login(LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse res = new AuthResponse();
        res.setMessage("User login successfully");
        res.setJwt(jwt);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);
        if(userDetails == null) {
            throw new BusinessException(ProjectExceptionEnum.USERNAME_NOT_FOUND);
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BusinessException(ProjectExceptionEnum.PASSWORD_NOT_FOUND);
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
