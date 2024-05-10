package com.project.todolist.domain.todo.dto;

import com.project.todolist.domain.todo.code.TodoStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

/**
 * Todo 정보 관련 요청 Dto
 */
@Slf4j
@Getter
public class TodoInfoRequestDto {
    private Long todoSeq;
    private String content;
    private LocalDate todoDate;
    private TodoStatus todoStatus;

    /**
     * Todo 생성 값 검증
     *
     * @return ValidationResult
     */
    public TodoInfoRequestDto.ValidationResult validateCreateRequestDto() {
        log.debug("validateCreateRequestDto:: TODO 생성 요청 값 검증 시작");
        if (this.content == null || this.content.isEmpty()) {
            return new TodoInfoRequestDto.ValidationResult(false, "내용을 입력해 주세요.");
        }
        return new TodoInfoRequestDto.ValidationResult(true, "");
    }

    /**
     * Todo 수정 값 검증
     *
     * @return ValidationResult
     */
    public TodoInfoRequestDto.ValidationResult validateUpdateRequestDto() {
        log.debug("nickName:: TODO 수정 요청 값 검증 시작");
        if (this.todoSeq == null || this.todoSeq < 0) {
            return new TodoInfoRequestDto.ValidationResult(false, "pk를 입력해 주세요.");
        }
        return new TodoInfoRequestDto.ValidationResult(true, "");
    }

    @Getter
    @AllArgsConstructor
    public static class ValidationResult {
        private boolean valid;
        private String message;
    }
}
