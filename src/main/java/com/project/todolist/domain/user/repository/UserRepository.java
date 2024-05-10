package com.project.todolist.domain.user.repository;

import com.project.todolist.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * 아이디 기준 조회
     *
     * @param loginId 로그인 아이디
     * @return UserEntity
     */
    UserEntity findByUserLoginId(String loginId);

    /**
     * 유저 PK 기준 조회
     *
     * @param userSeq 유저 PK
     * @return UserEntity
     */
    UserEntity findByUserSeq(Long userSeq);

    /**
     * 동일한 아이디 존재 여부
     *
     * @param userLoginId 로그인 아이디
     * @return boolean
     */
    boolean existsByUserLoginId(String userLoginId);
}
