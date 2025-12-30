package com.aenumz.whatsapcclone.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aenumz.whatsapcclone.model.dto.StringResponse;
import com.aenumz.whatsapcclone.model.dto.chat.ChatResponse;
import com.aenumz.whatsapcclone.service.ChatService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chats")
@Tag(name="Chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<StringResponse> createChat(@RequestParam("sender-id") String senderId, @RequestParam("recipient-id") String recipientId) {
        final String chatId = this.chatService.createChat(senderId, recipientId);
        StringResponse stringResponse = StringResponse.builder()
                .response(chatId)
                .build();

        return ResponseEntity.ok(stringResponse);
    }

    @GetMapping
    public ResponseEntity<List<ChatResponse>> getChatsByRecipient(Authentication authentication) {
        return ResponseEntity.ok(this.chatService.getChatsByRecipientId(authentication));
    }

}
