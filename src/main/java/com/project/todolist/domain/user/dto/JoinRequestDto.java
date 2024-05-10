package com.project.todolist.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 회원가입 요청 Dto
 */
@Getter
public class JoinRequestDto {
    private String loginId;
    private String password;
    private String nickName;

    /**
     * 회원가입 값 검증
     *
     * @return ValidationResult
     */
    public ValidationResult validateJoinRequestDto() {
        if (this.loginId == null || this.loginId.isEmpty() || this.loginId.length() > 20) {
            return new ValidationResult(false, "아이디는 1자 이상 20자 이하입니다.");
        } else if (this.password == null || this.password.isEmpty()) {
            return new ValidationResult(false, "비밀번호는 빈 값이 들어올 수 없습니다.");
        } else if (this.nickName == null || this.nickName.isEmpty() || this.nickName.length() > 10) {
            return new ValidationResult(false, "닉네임은 1자 이상 10자 이하입니다.");
        }
        return new ValidationResult(true, "");
    }

    @Getter
    @AllArgsConstructor
    public static class ValidationResult {
        private boolean valid;
        private String message;
    }
}
