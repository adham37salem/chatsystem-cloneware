package com.aenumz.whatsapcclone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aenumz.whatsapcclone.model.constant.ChatConstant;
import com.aenumz.whatsapcclone.model.entity.Chat;

@Repository
public interface ChatRepository extends CrudRepository<Chat, String> {
    @Query(name = ChatConstant.FIND_CHAT_BY_SENDER_ID )
    List<Chat> findChatBySenderId(@Param("senderId") String userId);
    @Query(name = ChatConstant.FIND_CHAT_BY_SENDER_ID_AND_RECEIVER)
    Optional<Chat> findChatBySenderIdAndReceiver(@Param("senderId") String senderId, @Param("receiverId") String receiverId);


}
