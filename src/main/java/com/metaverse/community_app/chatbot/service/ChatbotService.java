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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public ChatResponseDto processMessage(PrincipalDetails principalDetails, Long roomId, ChatRequestDto chatRequestDto) {
        // 1. 유효한 채팅방인지, 사용자가 권한이 있는지 확인
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다. ID: " + roomId));
        if (!chatRoom.getUser().getId().equals(principalDetails.getUser().getId())) {
            throw new IllegalArgumentException("해당 채팅방에 접근할 권한이 없습니다.");
        }
        // 2. (저장 1) 사용자 메시지를 DB에 저장
        saveDialog(chatRoom, SenderType.USER, chatRequestDto.getMessage());
        // 3. (호출) OpenAiService를 통해 AI 응답 생성
        String aiResponseMessage = openAiService.generate(chatRequestDto.getMessage());
        // 4. (저장 2) AI 응답을 DB에 저장
        saveDialog(chatRoom, SenderType.ASSISTANT, aiResponseMessage);

        return new ChatResponseDto(aiResponseMessage);
    }

    private void saveDialog(ChatRoom chatRoom, SenderType senderType, String content) {
        ChatDialog chatDialog = new ChatDialog(chatRoom, senderType, content);
        chatDialogRepository.save(chatDialog);
    }
}