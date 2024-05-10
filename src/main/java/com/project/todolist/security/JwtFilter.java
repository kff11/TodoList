package com.project.todolist.security;

import com.project.todolist.domain.user.code.UserRole;
import com.project.todolist.domain.user.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Jwt 검증 필터
 */
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        // Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.error("doFilterInternal:: token이 존재하지 않음");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1]; // Bearer 부분 제거 후 순수 토큰만 획득
        // 토큰 유효 시간 검증
        if (jwtUtil.isExpired(token)) {
            log.error("doFilterInternal:: token 유효시간 만료");
            filterChain.doFilter(request, response);
            return;
        }
        log.debug("doFilterInternal:: 토큰 검증 완료");
        
        Long userSeq = jwtUtil.getUserSeq(token);
        String nickName = jwtUtil.getNickName(token);
        String role = jwtUtil.getRole(token);

        UserEntity userEntity = UserEntity.builder()
                .userSeq(userSeq)
                .userNickName(nickName)
                .userRole(UserRole.valueOf(role))
                .build();

        UserPrincipal userPrincipal = new UserPrincipal(userEntity); // 회원 정보 객체 담기
        Authentication authToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken); // 세션에 임의로 등록
        filterChain.doFilter(request, response);
        log.debug("doFilterInternal:: SecurityContextHolder 등록 완료");
    }
}