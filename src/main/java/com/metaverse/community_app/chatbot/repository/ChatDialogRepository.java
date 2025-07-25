package com.metaverse.community_app.chatbot.repository;

import com.metaverse.community_app.chatbot.domain.ChatDialog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatDialogRepository extends JpaRepository<ChatDialog, Long> {
}