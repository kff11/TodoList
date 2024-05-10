package com.project.todolist.domain.todo.service;

import com.project.todolist.domain.todo.code.TodoStatus;
import com.project.todolist.domain.todo.entity.TodoEntity;
import com.project.todolist.domain.todo.repository.TodoRepository;
import com.project.todolist.domain.todo.dto.TodoInfoRequestDto;
import com.project.todolist.domain.todo.dto.PageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoRepository todoRepository;

    /**
     * Todo 조회
     *
     * @param page    현재 페이지
     * @param size    전체 목록 사이즈
     * @param userSeq 유저 PK
     * @return PageResponseDto<TodoEntity>
     */
    public PageResponseDto<TodoEntity> getPagingTodoList(int page, int size, Long userSeq) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<TodoEntity> todoPage = todoRepository.findByUserSeq(userSeq, pageRequest);

        return new PageResponseDto<>(
                todoPage.getContent(),
                todoPage.getNumber(),
                todoPage.getSize(),
                todoPage.getTotalElements()
        );
    }

    /**
     * 최근 1건 Todo 조회
     *
     * @param userSeq 유저 PK
     * @return TodoEntity
     */
    public TodoEntity getLastTodo(Long userSeq) {
        return todoRepository.findFirstByUserSeqOrderByUpdatedAtDesc(userSeq);
    }


    /**
     * Todo 생성
     *
     * @param content  Todo 내용
     * @param todoDate Todo 날짜
     * @param userSeq  유저 PK
     * @return TodoEntity
     */
    @Transactional
    public TodoEntity createTodo(String content, LocalDate todoDate, Long userSeq) {
        TodoEntity todoEntity = TodoEntity.builder()
                .todoStatus(TodoStatus.TODO)
                .todoContent(content)
                .todoDate(todoDate)
                .userSeq(userSeq)
                .build();
        return todoRepository.save(todoEntity);
    }

    /**
     * Todo 생성
     *
     * @param todoInfoRequestDto Todo 수정 요청 내용
     * @param userSeq            유저 PK
     * @return TodoEntity
     */
    @Transactional
    public TodoEntity updateTodoStatus(TodoInfoRequestDto todoInfoRequestDto, Long userSeq) {
        TodoEntity todoEntity = todoRepository.findByTodoSeqAndUserSeq(todoInfoRequestDto.getTodoSeq(), userSeq);
        if (todoEntity == null) {
            throw new RuntimeException("정상적인 요청이 아닙니다.");
        }

        if (todoInfoRequestDto.getContent() != null) {
            todoEntity.changeTodoContent(todoInfoRequestDto.getContent());
        }
        if (todoInfoRequestDto.getTodoDate() != null) {
            todoEntity.changeTodoDate(todoInfoRequestDto.getTodoDate());
        }
        if (todoInfoRequestDto.getTodoStatus() != null) {
            // 대기 상태로 변경 시 진행 중 상태가 아닌 경우
            if (todoInfoRequestDto.getTodoStatus() == TodoStatus.PENDING && todoEntity.getTodoStatus() != TodoStatus.IN_PROGRESS) {
                throw new RuntimeException("진행 중 상태에서만 대기 상태로 변경될 수 있습니다.");
            }
            todoEntity.changeTodoStatus(todoInfoRequestDto.getTodoStatus());
        }

        return todoRepository.save(todoEntity);
    }
}
