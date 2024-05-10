package com.project.todolist.domain.todo.controller;

import com.project.todolist.domain.todo.entity.TodoEntity;
import com.project.todolist.domain.todo.service.TodoService;
import com.project.todolist.domain.user.service.UserService;
import com.project.todolist.domain.todo.dto.TodoInfoRequestDto;
import com.project.todolist.domain.todo.dto.PageResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/todo")
public class TodoController {
    private final TodoService todoService;
    private final UserService userService;

    /**
     * 전체목록 조회
     */
    @GetMapping("")
    @Operation(summary = "전체목록 조회")
    public ResponseEntity<Object> getTodoList(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        Long userSeq = userService.getCurrentUserSeq();
        PageResponseDto<TodoEntity> pageResponseDto = todoService.getPagingTodoList(page, size, userSeq);
        return new ResponseEntity<>(pageResponseDto, HttpStatus.OK);
    }

    /**
     * 최신 조회
     */
    @GetMapping("/lastest")
    @Operation(summary = "가장 최근의 TODO 1개")
    public ResponseEntity<Object> getLastTodoList() {
        Long userSeq = userService.getCurrentUserSeq();
        TodoEntity todoEntity = todoService.getLastTodo(userSeq);
        if (todoEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(todoEntity, HttpStatus.OK);
    }

    /**
     * Todo 생성
     */
    @PostMapping("")
    @Operation(summary = "Todo 작성")
    public ResponseEntity<Object> createTodo(@RequestBody TodoInfoRequestDto todoInfoRequestDto) {
        // 요청 값 검증
        TodoInfoRequestDto.ValidationResult validationResult = todoInfoRequestDto.validateCreateRequestDto();
        if (!validationResult.isValid()) {
            log.error("createTodo:: validationResult.getMessage() => {}", validationResult.getMessage());
            return new ResponseEntity<>(validationResult.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.debug("createTodo:: TODO 생성 요청 값 검증 성공");

        Long userSeq = userService.getCurrentUserSeq();
        log.debug("createTodo:: userSeq => {}", userSeq);
        try {
            todoService.createTodo(todoInfoRequestDto.getContent(), todoInfoRequestDto.getTodoDate(), userSeq);
            log.debug("updateTodo:: TODO 생성 정상");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("updateTodo:: TODO 생성 실패");
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Todo 수정
     */
    @PutMapping("")
    @Operation(summary = "Todo 작성", description = "진행 중 상태에서만 대기 상태로 변경 가능")
    public ResponseEntity<Object> updateTodo(@RequestBody TodoInfoRequestDto todoInfoRequestDto) {
        // 요청 값 검증
        TodoInfoRequestDto.ValidationResult validationResult = todoInfoRequestDto.validateUpdateRequestDto();
        if (!validationResult.isValid()) {
            log.error("updateTodo:: validationResult.getMessage() => {}", validationResult.getMessage());
            return new ResponseEntity<>(validationResult.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.debug("updateTodo:: TODO 수정 요청 값 검증 성공");

        Long userSeq = userService.getCurrentUserSeq();
        try {
            todoService.updateTodoStatus(todoInfoRequestDto, userSeq);
            log.debug("updateTodo:: TODO 수정 정상");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
