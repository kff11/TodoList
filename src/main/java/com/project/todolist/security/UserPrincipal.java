package com.project.todolist.security;

import com.project.todolist.domain.user.code.UserRole;
import com.project.todolist.domain.user.entity.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * 인증 정보 클래스
 */
@Getter
public class UserPrincipal implements UserDetails {
    private final UserEntity userEntity;

    public UserPrincipal(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    private GrantedAuthority getAuthority(UserRole role) {
        return new SimpleGrantedAuthority(role.toString());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();

        switch (userEntity.getUserRole()) {
            case ROLE_ADMIN:
                authorityList.add(getAuthority(UserRole.ROLE_ADMIN));
            case ROLE_USER:
                authorityList.add(getAuthority(UserRole.ROLE_USER));
        }

        return authorityList;
    }

    @Override
    public String getPassword() {
        return userEntity.getUserPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUserLoginId();
    }

    public String getNickName() {
        return userEntity.getUserNickName();
    }

    public Long getUserSeq() {
        return userEntity.getUserSeq();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
