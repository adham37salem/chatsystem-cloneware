package com.aenumz.whatsapcclone.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aenumz.whatsapcclone.model.dto.chat.ChatResponse;
import com.aenumz.whatsapcclone.model.entity.Chat;
import com.aenumz.whatsapcclone.model.entity.User;
import com.aenumz.whatsapcclone.model.mapper.ChatMapper;
import com.aenumz.whatsapcclone.repository.ChatRepository;
import com.aenumz.whatsapcclone.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatMapper mapper;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ChatResponse> getChatsByRecipientId(Authentication currentUser) {
        final String userId = currentUser.getName();
        return this.chatRepository.findChatBySenderId(userId)
                .stream()
                .map(chat -> this.mapper.toChatResponse(chat, userId))
                .toList();
    }

    public String createChat(String senderId, String recipientId) {
        Optional<Chat> existingChat = this.chatRepository.findChatBySenderIdAndReceiver(senderId, recipientId);
        if (existingChat.isPresent() && !existingChat.get().getMessages().isEmpty()) {
            return existingChat.get().getId();
        }

        User sender = this.userRepository.findUserByPublicId(senderId)
                .orElseThrow(() -> new EntityNotFoundException("User with Id " + senderId + " not found"));
        User recipient = this.userRepository.findUserByPublicId(senderId)
                .orElseThrow(() -> new EntityNotFoundException("User with Id " + recipientId + " not found"));

        Chat chat = new Chat();
        chat.setSender(sender);
        chat.setRecipient(recipient);
        Chat savedChat = this.chatRepository.save(chat);
        return savedChat.getId();
    }


}
