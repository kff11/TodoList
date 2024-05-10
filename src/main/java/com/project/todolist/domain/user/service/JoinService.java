package com.project.todolist.domain.user.service;

import com.project.todolist.domain.user.repository.UserRepository;
import com.project.todolist.domain.user.code.UserRole;
import com.project.todolist.domain.user.entity.UserEntity;
import com.project.todolist.domain.user.dto.JoinRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 회원가입 로직
     *
     * @param joinRequestDto 회원가입 요청 값
     */
    public boolean joinProcess(JoinRequestDto joinRequestDto) {
        String loginId = joinRequestDto.getLoginId();
        String password = joinRequestDto.getPassword();
        String nickName = joinRequestDto.getNickName();

        // 아이디 중복 여부 조회
        boolean isExist = userRepository.existsByUserLoginId(loginId);
        if (isExist) {
            return false;
        }

        UserEntity userEntity = UserEntity.builder()
                .userLoginId(loginId)
                .userPassword(bCryptPasswordEncoder.encode(password))
                .userNickName(nickName)
                .userRole(UserRole.ROLE_USER)
                .build();

        userRepository.save(userEntity);
        return true;
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public void deleteUser(Long userSeq) {
        UserEntity userEntity = userRepository.findByUserSeq(userSeq);
        if (userEntity != null) {
            userRepository.delete(userEntity);
        } else {
            throw new UsernameNotFoundException("deleteUser:: 회원을 찾을 수 없음 => " + userSeq);
        }
    }
}
