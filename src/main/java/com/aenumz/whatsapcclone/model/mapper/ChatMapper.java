package com.aenumz.whatsapcclone.model.mapper;

import org.springframework.stereotype.Service;

import com.aenumz.whatsapcclone.model.dto.chat.ChatResponse;
import com.aenumz.whatsapcclone.model.entity.Chat;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMapper {
    public ChatResponse toChatResponse(Chat chat, String senderId) {
        return ChatResponse
                .builder()
                .id(chat.getId())
                .name(chat.getChatName(senderId))
                .unreadCount(chat.getUnreadMessageCount(senderId))
                .lastMessage(chat.getLastMessage())
                .isRecipientOnline(chat.getRecipient().isUserOnline())
                .senderId(chat.getSender().getId())
                .recipientId(chat.getRecipient().getId())
                .lastMessageTime(chat.getLastMessageTime())
                .build();
    }
}
