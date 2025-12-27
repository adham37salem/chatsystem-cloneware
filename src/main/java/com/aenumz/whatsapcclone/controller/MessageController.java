package com.aenumz.whatsapcclone.controller;

import com.aenumz.whatsapcclone.model.dto.message.MessageRequest;
import com.aenumz.whatsapcclone.model.dto.message.MessageResponse;
import com.aenumz.whatsapcclone.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveMessage(@RequestBody MessageRequest request) {
        this.messageService.saveMessage(request);
    }

    @PostMapping(value = "/upload-media", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadMediaMessage(@RequestParam("chat-id") String chatId, @RequestParam("file") MultipartFile file, Authentication authentication) {
        this.messageService.uploadMediaMessage(chatId, file, authentication);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void setMessagesToSeen(@RequestParam("chat-id") String chatId, Authentication authentication) {
        this.messageService.setMessagesToSeen(authentication, chatId);
    }

    @GetMapping("/chat/{chat-id}")
    public ResponseEntity<List<MessageResponse>> getMessages(@PathVariable("chat-id") String chatId) {
        return ResponseEntity.ok(this.messageService.findChatMessages(chatId));
    }
}
