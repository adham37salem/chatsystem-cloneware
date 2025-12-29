package com.aenumz.whatsapcclone.notification;

import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;
    public void sendNotification(@NonNull String userId, @NonNull Notification notification) {
        log.info("Sending notification to user {} with payload {}", userId, notification);
        messagingTemplate.convertAndSendToUser(userId, "/chat", notification);
    }
}
