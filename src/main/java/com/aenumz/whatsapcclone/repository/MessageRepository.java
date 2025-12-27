package com.aenumz.whatsapcclone.repository;

import com.aenumz.whatsapcclone.model.constant.MessageConstant;
import com.aenumz.whatsapcclone.model.dto.message.MessageResponse;
import com.aenumz.whatsapcclone.model.entity.Chat;
import com.aenumz.whatsapcclone.model.entity.Message;
import com.aenumz.whatsapcclone.model.entity.MessageState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query(name = MessageConstant.FIND_MESSAGE_BY_CHAT_ID)
    List<Message> findMessagesByChatId(@Param("chatId") String chatId);
    @Query(name = MessageConstant.SET_MESSAGES_TO_SEEN_BY_CHAT)
    void setMessagesToSeenByChatId(@Param("chatId") String chatId, @Param("newState") MessageState state);
}
