package com.aenumz.whatsapcclone.model.mapper;

import com.aenumz.whatsapcclone.model.dto.user.UserResponse;
import com.aenumz.whatsapcclone.model.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserMapper {
    public User fromTokenAttributes(Map<String, Object> attributes) {
        User user = new User();
        if (attributes.containsKey("sub")) {
            user.setId(attributes.get("sub").toString());
        }
        if (attributes.containsKey("given_name")) {
            user.setFirstName(attributes.get("given_name").toString());
        }

        else if (attributes.containsKey("nickname")) {
            user.setFirstName(attributes.get("nickname").toString());
        }

        if (attributes.containsKey("family_name")) {
            user.setLastName(attributes.get("family_name").toString());
        }
        if (attributes.containsKey("email")) {
            user.setEmail(attributes.get("email").toString());
        }
        user.setLastName(LocalDateTime.now().toString());
        return user;
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse
                .builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .lastSeen(user.getLastSeen())
                .isOnline(user.isUserOnline())
                .build();
    }
}
