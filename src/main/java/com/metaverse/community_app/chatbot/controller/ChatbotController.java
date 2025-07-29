package com.metaverse.community_app.chatbot.controller;

import com.metaverse.community_app.auth.domain.PrincipalDetails;
import com.metaverse.community_app.chatbot.dto.ChatDialogResponseDto;
import com.metaverse.community_app.chatbot.dto.ChatRequestDto;
import com.metaverse.community_app.chatbot.dto.ChatResponseDto;
import com.metaverse.community_app.chatbot.dto.ChatRoomResponseDto;
import com.metaverse.community_app.chatbot.service.ChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
public class ChatbotController {
    private final ChatbotService chatbotService;

    @PostMapping("/rooms")
    public ResponseEntity<ChatRoomResponseDto> createRoom(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody Map<String, String> request) {
        String title = request.get("title");
        if (title == null || title.isBlank()) {
            return ResponseEntity.badRequest().build();
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

    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoomResponseDto>> getRooms(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(chatbotService.findRoomsByUser(principalDetails));
    }

    @GetMapping("/rooms/{roomId}/messages")
    public ResponseEntity<List<ChatDialogResponseDto>> getChatDialogs(
            @PathVariable Long roomId,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(chatbotService.getChatDialogs(roomId, principalDetails));
    }
}