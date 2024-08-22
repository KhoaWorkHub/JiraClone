package com.khoa.managementsystem.controller.impl;

import com.khoa.managementsystem.controller.IMessageController;
import com.khoa.managementsystem.exception.BusinessException;
import com.khoa.managementsystem.exception.ProjectExceptionEnum;
import com.khoa.managementsystem.model.Chat;
import com.khoa.managementsystem.model.Message;
import com.khoa.managementsystem.model.User;
import com.khoa.managementsystem.request.CreateMessageRequest;
import com.khoa.managementsystem.security.JwtProvider;
import com.khoa.managementsystem.service.MessageService;
import com.khoa.managementsystem.service.ProjectService;
import com.khoa.managementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController extends BaseController implements IMessageController {

    private final MessageService messageService;

    private final UserService userService;

    private final ProjectService projectService;

    @Override
    public ResponseEntity<Object> sendMessage(CreateMessageRequest request, String jwt) {
//        User user = userService.findUserById(request.getSenderId());
//        if (user == null) {
//            throw new BusinessException(ProjectExceptionEnum.USER_NOT_FOUND);
//        }
        User user = userService.findUserProfileByJwt(jwt);
        if (user == null) {
            String email = JwtProvider.getEmailFromToken(jwt);
            throw new BusinessException(ProjectExceptionEnum.valueOf(ProjectExceptionEnum.USERNAME_NOT_FOUND.formatMessage(email)));
        }
        Chat chat = projectService.getProjectById(request.getProjectId()).getChat();
        if (chat == null) {
            throw new BusinessException(ProjectExceptionEnum.CHAT_NOT_FOUND);
        }
//        Message sendMessage = messageService.sendMessage(request.getSenderId(), request.getProjectId(), request.getContent());
        return ok(messageService.sendMessage(request.getSenderId(), request.getProjectId(), request.getContent()));
    }

    @Override
    public ResponseEntity<Object> getMessagesByChatId(Long projectId) {
        return ok(messageService.getMessagesByProjectId(projectId));
    }
}
