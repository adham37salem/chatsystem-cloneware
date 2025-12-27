package com.aenumz.whatsapcclone.service;

import com.aenumz.whatsapcclone.model.dto.user.UserResponse;
import com.aenumz.whatsapcclone.model.entity.User;
import com.aenumz.whatsapcclone.model.mapper.UserMapper;
import com.aenumz.whatsapcclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponse> getAllUsersExceptSelf(Authentication authUser) {
        return this.userRepository.findAllUserExceptSelf(authUser.getName())
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

}
