package com.aenumz.whatsapcclone.service;

import com.aenumz.whatsapcclone.model.dto.message.MessageRequest;
import com.aenumz.whatsapcclone.model.dto.message.MessageResponse;
import com.aenumz.whatsapcclone.model.entity.Chat;
import com.aenumz.whatsapcclone.model.entity.Message;
import com.aenumz.whatsapcclone.model.entity.MessageState;
import com.aenumz.whatsapcclone.model.entity.MessageType;
import com.aenumz.whatsapcclone.model.mapper.MessageMapper;
import com.aenumz.whatsapcclone.repository.ChatRepository;
import com.aenumz.whatsapcclone.repository.MessageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper messageMapper;
    private final FileService fileService;

    public void saveMessage(MessageRequest request) {
        Chat chat = this.chatRepository
                .findById(request.getChatId())
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));
        Message newMessage = Message
                .builder()
                .content(request.getContent())
                .chat(chat)
                .senderId(request.getSenderId())
                .recipientId(request.getRecipientId())
                .type(request.getType())
                .state(MessageState.SENT)
                .build();
        this.messageRepository.save(newMessage);
        // todo notification system
    }

    public List<MessageResponse> findChatMessages(String chatId) {
        return this.messageRepository.findMessagesByChatId(chatId)
                .stream()
                .map(this.messageMapper::toMessageResponse)
                .toList();
    }

    @Transactional
    public void setMessagesToSeen(Authentication authentication, String chatId) {
        Chat chat = this.chatRepository
                .findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));
        final String recipientId = this.getRecipientId(authentication, chat);
        this.messageRepository.setMessagesToSeenByChatId(chatId, MessageState.SEEN);

        // todo notification system
    }

    private String getRecipientId(Authentication authentication, Chat chat) {
        if (chat.getSender().getId().equals(authentication.getName())) {
            return chat.getRecipient().getId();
        }
        return chat.getSender().getId();
    }

    public void uploadMediaMessage(String chatId, MultipartFile file, Authentication authentication) {
        Chat chat = this.chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat Not Found"));
        final String senderId = this.getSenderId(chat, authentication);
        final String recipientId = this.getRecipientId(authentication, chat);
        final String filePath = fileService.saveFile(file, senderId);
        Message message = Message.builder()
                .chat(chat)
                .senderId(senderId)
                .recipientId(recipientId)
                .type(MessageType.IMAGE)
                .state(MessageState.SENT)
                .mediaFilePath(filePath)
                .build();
        this.messageRepository.save(message);
        // todo notification system

    }

    private String getSenderId(Chat chat, Authentication authentication) {
        if (chat.getSender().getId().equals(authentication.getName())) {
            return chat.getSender().getId();
        }
        return chat.getRecipient().getId();
    }
}
