package com.aenumz.whatsapcclone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aenumz.whatsapcclone.model.constant.UserConstant;
import com.aenumz.whatsapcclone.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query(name = UserConstant.FIND_USER_BY_EMAIL)
    Optional<User> findUserByEmail(@Param("email") String userEmail);
    @Query(name = UserConstant.FIND_USER_BY_PUBLIC_ID)
    Optional<User> findUserByPublicId(@Param("publicId") String userId);
    @Query(name = UserConstant.FIND_ALL_USER_EXCEPT_SELF)
    List<User> findAllUserExceptSelf(@Param("publicId") String publicId);

}
