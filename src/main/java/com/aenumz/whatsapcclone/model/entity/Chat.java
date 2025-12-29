package com.aenumz.whatsapcclone.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.aenumz.whatsapcclone.common.BaseAuditingEntity;
import com.aenumz.whatsapcclone.model.constant.ChatConstant;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chats")
@NamedQuery(name = ChatConstant.FIND_CHAT_BY_SENDER_ID, query = "SELECT DISTINCT c from Chat c where c.sender.id = :senderId OR c.recipient.id =: senderId ORDER BY createdDate DESC")
@NamedQuery(name = ChatConstant.FIND_CHAT_BY_SENDER_ID_AND_RECEIVER, query = "SELECT DISTINCT c from Chat c where (c.sender.id = :senderId AND c.recipient.id = :receiverId) OR (c.sender.id = :receiverId AND c.recipient.id = :senderId)")
public class Chat extends BaseAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER)
    @OrderBy("createdDate DESC ")
    private List<Message> messages;

    /**
     * Gets the name of the chat.
     * If the recipient is the one who sent the message, the name of the sender is returned.
     * Otherwise, the name of the recipient is returned.
     *
     * @param senderId the id of the sender
     * @return the name of the chat
     */
    @Transient
    public String getChatName(final String senderId) {
        if (recipient.getId().equals(senderId)) {
            return sender.getFirstName() + " " + sender.getLastName();
        }
        else {
            return recipient.getFirstName() + " " + recipient.getLastName();
        }
    }

    /**
     * Returns the number of unread messages in the chat for the given sender id.
     * A message is considered unread if the recipient id matches the given sender id and the message state is SENT.
     *
     * @param senderId the id of the sender
     * @return the number of unread messages
     */
    @Transient
    public long getUnreadMessageCount(final String senderId) {
        return messages
                .stream()
                .filter(message -> message.getRecipientId().equals(senderId))
                .filter(message -> MessageState.SENT == message.getState())
                .count();
    }

    /**
     * Returns the content of the last message in the chat, or null if the chat is empty.
     * If the last message is not a text message, "Attachment" is returned.
     *
     * @return the content of the last message in the chat, or null if the chat is empty
     */
    @Transient
    public String getLastMessage() {
        if (messages != null && !messages.isEmpty()) {
            if (messages.get(0).getType() != MessageType.TEXT) {
                return "Attachment";
            }
            return messages.get(0).getContent();
        }
        return null;
    }

    /**
     * Returns the timestamp of the last message in the chat, or null if the chat is empty.
     *
     * @return the timestamp of the last message in the chat, or null if the chat is empty
     */
    @Transient
    public LocalDateTime getLastMessageTime() {
        if (messages != null && !messages.isEmpty()) {
            return messages.get(0).getCreatedDate();
        }
        return null;
    }

}
