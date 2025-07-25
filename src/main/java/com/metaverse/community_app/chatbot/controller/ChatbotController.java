package com.metaverse.community_app.chatbot.controller;

import com.metaverse.community_app.auth.domain.PrincipalDetails;
import com.metaverse.community_app.chatbot.dto.ChatRequestDto;
import com.metaverse.community_app.chatbot.dto.ChatResponseDto;
import com.metaverse.community_app.chatbot.service.ChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
public class ChatbotController {
    private final ChatbotService chatbotService;

    @PostMapping("/rooms")
    public ResponseEntity<?> createRoom(
            @RequestBody Map<String, String> request,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        String title = request.get("title");
        if (title == null || title.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "채팅방 제목은 필수입니다."));
        }
        return ResponseEntity.ok(chatbotService.createRoom(principalDetails, title));
    }

    @PostMapping("/rooms/{roomId}/messages")
    public ResponseEntity<ChatResponseDto> postMessage(
            @PathVariable Long roomId,
            @RequestBody ChatRequestDto chatRequestDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ChatResponseDto response = chatbotService.processMessage(principalDetails, roomId, chatRequestDto);
        return ResponseEntity.ok(response);
    }
}