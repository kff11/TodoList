package com.project.todolist.domain.todo.repository;

import com.project.todolist.domain.todo.entity.TodoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    /**
     * 로그인 아이디 기준 조회
     *
     * @param userSeq  유저 PK
     * @param pageable 페이징 파라메터
     * @return List<TodoEntity>
     */
    Page<TodoEntity> findByUserSeq(Long userSeq, Pageable pageable);

    /**
     * Todo PK, 로그인 아이디 기준 조회
     *
     * @param todoSeq Todo PK
     * @param userSeq 유저 PK
     * @return List<TodoEntity>
     */
    TodoEntity findByTodoSeqAndUserSeq(Long todoSeq, Long userSeq);

    /**
     * 최근 1건 조회
     *
     * @return TodoEntity
     */
    TodoEntity findFirstByUserSeqOrderByUpdatedAtDesc(Long userSeq);
}
