package com.metaverse.community_app.chatbot.dto;

import com.metaverse.community_app.chatbot.domain.ChatRoom;
import lombok.Getter;

@Getter
public class ChatRoomResponseDto {
    private Long id;
    private String title;
    private String authorNickname;

    public ChatRoomResponseDto(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.title = chatRoom.getTitle();
        this.authorNickname = chatRoom.getUser().getNickname();
    }
}