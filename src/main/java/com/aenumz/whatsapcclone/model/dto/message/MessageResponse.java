package com.aenumz.whatsapcclone.model.dto.message;

import com.aenumz.whatsapcclone.model.entity.MessageState;
import com.aenumz.whatsapcclone.model.entity.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private Long id;
    private String content;
    private MessageState state;
    private MessageType type;
    private String senderId;
    private String recipientId;
    private LocalDateTime createdDate;
    private byte[] media;

}
