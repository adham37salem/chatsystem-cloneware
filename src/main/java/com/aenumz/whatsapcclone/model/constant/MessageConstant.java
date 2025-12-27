package com.aenumz.whatsapcclone.model.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageConstant {
    public static final String FIND_MESSAGE_BY_CHAT_ID = "Messages.findByChatId";
    public static final String SET_MESSAGES_TO_SEEN_BY_CHAT = "Messages.setMessagesToSeenByChat";
}
