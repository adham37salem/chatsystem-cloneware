package com.aenumz.whatsapcclone.model.dto.message;

import com.aenumz.whatsapcclone.model.entity.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    private String content;
    private String senderId;
    private String recipientId;
    private MessageType type;
    private String chatId;


}
