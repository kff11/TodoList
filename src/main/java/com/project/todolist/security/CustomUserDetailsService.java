package com.project.todolist.security;

import com.project.todolist.domain.user.repository.UserRepository;
import com.project.todolist.domain.user.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("loadUserByUsername:: loginId => {}", username);
        UserEntity userEntity = userRepository.findByUserLoginId(username);

        if (userEntity == null) {
            log.error("loadUserByUsername:: 로그인 실패");
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        log.debug("loadUserByUsername:: 로그인 성공");
        return new UserPrincipal(userEntity);
    }
}