package com.aenumz.whatsapcclone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aenumz.whatsapcclone.model.constant.MessageConstant;
import com.aenumz.whatsapcclone.model.entity.Message;
import com.aenumz.whatsapcclone.model.entity.MessageState;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query(name = MessageConstant.FIND_MESSAGE_BY_CHAT_ID)
    List<Message> findMessagesByChatId(@Param("chatId") String chatId);
    @Query(name = MessageConstant.SET_MESSAGES_TO_SEEN_BY_CHAT)
    @Modifying
    void setMessagesToSeenByChatId(@Param("chatId") String chatId, @Param("newState") MessageState state);
}
