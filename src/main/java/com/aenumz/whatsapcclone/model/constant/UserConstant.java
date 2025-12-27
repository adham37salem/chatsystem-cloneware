package com.aenumz.whatsapcclone.model.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserConstant {
    public static final String FIND_USER_BY_EMAIL = "Users.findUserByEmail";
    public static final String FIND_ALL_USER_EXCEPT_SELF = "Users.findAllUserExceptSelf";
    public static final String FIND_USER_BY_PUBLIC_ID = "Users.findUserByPublicId";
}
