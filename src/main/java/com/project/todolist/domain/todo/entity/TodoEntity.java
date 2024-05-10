package com.project.todolist.domain.todo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.todolist.domain.todo.code.TodoStatus;
import com.project.todolist.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Table(name = "todoList")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoSeq;

    @Enumerated(EnumType.STRING)
    private TodoStatus todoStatus;

    @NotBlank
    private String todoContent;

    private LocalDate todoDate;

    @NotNull
    private Long userSeq;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Todo 내용 수정
     *
     * @param todoContent Todo 내용
     */
    public void changeTodoContent(String todoContent) {
        this.todoContent = todoContent;
    }

    /**
     * Todo 상태 수정
     *
     * @param todoStatus Todo 상태
     */
    public void changeTodoStatus(TodoStatus todoStatus) {
        this.todoStatus = todoStatus;
    }

    /**
     * Todo 일시 수정
     *
     * @param todoDate Todo 일시
     */
    public void changeTodoDate(LocalDate todoDate) {
        this.todoDate = todoDate;
    }
}
