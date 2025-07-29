package com.metaverse.community_app.chatbot.dto;

import com.metaverse.community_app.chatbot.domain.ChatDialog;
import com.metaverse.community_app.chatbot.domain.SenderType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatDialogResponseDto {
    private Long id;
    private SenderType senderType;
    private String content;
    private LocalDateTime createdAt;

    public ChatDialogResponseDto(ChatDialog dialog) {
        this.id = dialog.getId();
        this.senderType = dialog.getSenderType();
        this.content = dialog.getContent();
        this.createdAt = dialog.getCreatedAt();
    }
}