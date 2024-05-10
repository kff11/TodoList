package com.project.todolist.domain.user.controller;

import com.project.todolist.domain.user.service.JoinService;
import com.project.todolist.domain.user.service.UserService;
import com.project.todolist.domain.user.dto.JoinRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/join")
public class JoinController {
    private final JoinService joinService;
    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping("")
    @Operation(summary = "회원가입")
    public ResponseEntity<Object> createUser(@RequestBody JoinRequestDto joinRequestDto) {
        // 요청 데이터 유효성 검사
        JoinRequestDto.ValidationResult validationResult = joinRequestDto.validateJoinRequestDto();
        if (!validationResult.isValid()) {
            return new ResponseEntity<>(validationResult.getMessage(), HttpStatus.BAD_REQUEST);
        }

        boolean result = joinService.joinProcess(joinRequestDto);
        if (!result) {
            return new ResponseEntity<>("중복된 아이디가 존재합니다.", HttpStatus.CONFLICT);
        }
        log.debug("createUser:: 회원가입 정상");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 회원탈퇴
     */
    @DeleteMapping("")
    @Operation(summary = "회원탈퇴")
    public ResponseEntity<Object> dropUser() {
        Long userSeq = userService.getCurrentUserSeq();
        try {
            joinService.deleteUser(userSeq);
            log.debug("createUser:: 회원탈퇴 정상");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("createUser:: 회원탈퇴 오류");
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
