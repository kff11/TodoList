package com.project.todolist.domain.user.service;

import com.project.todolist.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    /**
     * SecurityContextHolder에 저장되어 있는 유저 PK 조회
     *
     * @return loginId
     */
    public Long getCurrentUserSeq() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
            return userDetails.getUserSeq();
        }
        return null;
    }
}
