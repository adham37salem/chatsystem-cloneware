package com.aenumz.whatsapcclone.model.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatConstant {
    public static final String FIND_CHAT_BY_SENDER_ID = "Chat.findChatBySenderId";
    public static final String FIND_CHAT_BY_SENDER_ID_AND_RECEIVER = "Chat.findChatBySenderIdAndReceiver";
}
