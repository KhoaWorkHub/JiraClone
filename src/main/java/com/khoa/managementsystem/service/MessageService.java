package com.khoa.managementsystem.service;

import com.khoa.managementsystem.model.Message;

import java.util.List;

public interface MessageService {

    Message sendMessage(Long senderId, Long chatId, String content);

    List<Message> getMessagesByProjectId(Long projectId);
}
