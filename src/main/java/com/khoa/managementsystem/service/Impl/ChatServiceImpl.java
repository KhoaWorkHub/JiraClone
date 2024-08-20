package com.khoa.managementsystem.service.Impl;

import com.khoa.managementsystem.model.Chat;
import com.khoa.managementsystem.repository.ChatRepository;
import com.khoa.managementsystem.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    @Override
    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }
}
