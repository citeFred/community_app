package com.metaverse.community_app.chatbot.service;

import com.metaverse.community_app.ai.service.OpenAiService;
import com.metaverse.community_app.auth.domain.PrincipalDetails;
import com.metaverse.community_app.auth.domain.User;
import com.metaverse.community_app.chatbot.domain.ChatDialog;
import com.metaverse.community_app.chatbot.domain.ChatRoom;
import com.metaverse.community_app.chatbot.domain.SenderType;
import com.metaverse.community_app.chatbot.dto.ChatRequestDto;
import com.metaverse.community_app.chatbot.dto.ChatResponseDto;
import com.metaverse.community_app.chatbot.repository.ChatDialogRepository;
import com.metaverse.community_app.chatbot.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatbotService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatDialogRepository chatDialogRepository;
    private final OpenAiService openAiService;

    @Transactional
    public ChatRoom createRoom(PrincipalDetails principalDetails, String title) {
        User currentUser = principalDetails.getUser();
        ChatRoom newRoom = new ChatRoom(currentUser, title);
        return chatRoomRepository.save(newRoom);
    }

    /**
     * 채팅방에 메시지 전송 및 응답 처리 with 대화기록 기억 (최종 버전)
     */
    @Transactional
    public ChatResponseDto processMessage(PrincipalDetails principalDetails, Long roomId, ChatRequestDto chatRequestDto) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다. ID: " + roomId));
        if (!chatRoom.getUser().getId().equals(principalDetails.getUser().getId())) {
            throw new IllegalArgumentException("해당 채팅방에 접근할 권한이 없습니다.");
        }

        // 1. (DB 저장) 사용자 메시지 저장
        saveDialog(chatRoom, SenderType.USER, chatRequestDto.getMessage());

        // 2. (데이터 준비) AI에게 전달할 대화 기록(List<Message>) 준비
        List<Message> history = chatRoom.getChatDialogs().stream()
                .map(dialog -> dialog.getSenderType() == SenderType.USER
                        ? (Message) new UserMessage(dialog.getContent())
                        : (Message) new AssistantMessage(dialog.getContent()))
                .collect(Collectors.toList());

        // 3. (호출) OpenAiService에 대화 기록을 전달하고 응답 받기
        String aiResponseMessage = openAiService.generateWithHistory(history);

        // 4. (DB 저장) AI 응답 저장
        saveDialog(chatRoom, SenderType.ASSISTANT, aiResponseMessage);

        return new ChatResponseDto(aiResponseMessage);
    }

    private void saveDialog(ChatRoom chatRoom, SenderType senderType, String content) {
        ChatDialog chatDialog = new ChatDialog(chatRoom, senderType, content);
        chatDialogRepository.save(chatDialog);
    }
}