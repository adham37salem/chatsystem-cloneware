package com.aenumz.whatsapcclone.model.entity;

import com.aenumz.whatsapcclone.common.BaseAuditingEntity;
import com.aenumz.whatsapcclone.model.constant.UserConstant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@NamedQuery(name = UserConstant.FIND_USER_BY_EMAIL, query = "SELECT u FROM User u WHERE u.email = :email")
@NamedQuery(name = UserConstant.FIND_ALL_USER_EXCEPT_SELF, query = "SELECT u FROM User u WHERE u.id != :publicId")
@NamedQuery(name = UserConstant.FIND_USER_BY_PUBLIC_ID, query = "SELECT u FROM User u WHERE u.id = :publicId")
public class User extends BaseAuditingEntity {

    private static final int LAST_ACTIVE_INTERVAL = 5;
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDateTime lastSeen;

    @OneToMany(mappedBy = "sender")
    private List<Chat> chatsAsSender;

    @OneToMany(mappedBy = "recipient")
    private List<Chat> chatsAsRecipient;

    /**
     * Check if the user is online.
     * A user is considered online if the last seen time is within the last 5 minutes.
     * @return true if the user is online, false otherwise
     */
    @Transient
    public boolean isUserOnline() {
        return lastSeen != null && lastSeen.isAfter(LocalDateTime.now().minusMinutes(LAST_ACTIVE_INTERVAL));
    }


}
