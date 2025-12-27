package com.aenumz.whatsapcclone.interceptor;

import com.aenumz.whatsapcclone.model.entity.User;
import com.aenumz.whatsapcclone.model.mapper.UserMapper;
import com.aenumz.whatsapcclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
//logging
@Slf4j
public class UserSynchronizer {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public void syncWithIdentityProvider(Jwt token) {
        log.info("Synchronizing user IDP");
        this.getUserEmail(token).ifPresent(userEmail -> {
            log.info("Synchronizing user having email {}", userEmail);
//            Optional<User> optionalUser = this.userRepository.findUserByEmail(userEmail);
            User user = this.userMapper.fromTokenAttributes(token.getClaims());
//            optionalUser.ifPresent(value -> user.setId(optionalUser.get().getId()));
            this.userRepository.save(user);
        });
    }

    private Optional<String> getUserEmail(Jwt token) {
        Map<String, Object> attributes = token.getClaims();
        if (attributes.containsKey("email")) {
            return Optional.of(attributes.get("email").toString());
        }
        return Optional.empty();
    }
}
