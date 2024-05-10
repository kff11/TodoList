package com.project.todolist.domain.todo.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TODO List 상태 코드
 */
@AllArgsConstructor
@Getter
public enum TodoStatus {
    TODO("TODO", "할일"),
    IN_PROGRESS("IN_PROGRESS", "진행 중"),
    DONE("DONE", "완료"),
    PENDING("PENDING", "대기");

    private final String name;
    private final String desc;
}
