package com.khoa.managementsystem.service.Impl;

import com.khoa.managementsystem.exception.BusinessException;
import com.khoa.managementsystem.exception.ProjectExceptionEnum;
import com.khoa.managementsystem.model.Chat;
import com.khoa.managementsystem.model.Message;
import com.khoa.managementsystem.model.User;
import com.khoa.managementsystem.repository.MessageRepository;
import com.khoa.managementsystem.repository.UserRepository;
import com.khoa.managementsystem.service.MessageService;
import com.khoa.managementsystem.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final ProjectService projectService;

    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() ->
                        new BusinessException(ProjectExceptionEnum
                                .valueOf(ProjectExceptionEnum
                                        .ISSUE_NOT_FOUND_WITH_ID.formatMessage(senderId))));

        Chat chat = projectService.getProjectById(projectId).getChat();

        Message message = new Message();

        message.setSender(sender);
        message.setChat(chat);
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());

        Message savedMessage = messageRepository.save(message);
        chat.getMessages().add(savedMessage);

        return savedMessage;
    }

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) {
        Chat chat = projectService.getChatByProjectId(projectId);
        return messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
    }
}
