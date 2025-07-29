package com.metaverse.community_app.chatbot.repository;

import com.metaverse.community_app.auth.domain.User;
import com.metaverse.community_app.chatbot.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findAllByUserOrderByCreatedAtDesc(User user);
}