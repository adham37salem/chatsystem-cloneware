package com.aenumz.whatsapcclone.model.mapper;

import org.springframework.stereotype.Service;

import com.aenumz.whatsapcclone.model.dto.message.MessageResponse;
import com.aenumz.whatsapcclone.model.entity.Message;
import com.aenumz.whatsapcclone.util.FileUtil;

import lombok.Builder;
import lombok.Data;

@Service
@Data
@Builder
public class MessageMapper {
    public MessageResponse toMessageResponse(Message message) {
        return MessageResponse
                .builder()
                .id(message.getId())
                .senderId(message.getSenderId())
                .recipientId(message.getRecipientId())
                .content(message.getContent())
                .type(message.getType())
                .state(message.getState())
                .createdDate(message.getCreatedDate())
                // todo read the media files
                .media(FileUtil.readFileFromLocation(message.getMediaFilePath()))
                .build();

    }
}
