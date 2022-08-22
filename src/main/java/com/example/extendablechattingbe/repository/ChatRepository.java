package com.example.extendablechattingbe.repository;

import com.example.extendablechattingbe.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
