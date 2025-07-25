package com.metaverse.community_app.chatbot.repository;

import com.metaverse.community_app.chatbot.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}