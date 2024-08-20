package com.khoa.managementsystem.repository;

import com.khoa.managementsystem.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
