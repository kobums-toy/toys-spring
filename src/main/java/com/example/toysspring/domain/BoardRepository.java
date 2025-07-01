package com.example.toysspring.domain;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BoardRepository extends JpaRepository<Board, String>, JpaSpecificationExecutor<Board> {
    List<Board> findAll();
    Optional<Board> findByBoardId(Long BoardId);
    Optional<Board> findByBoardTitle(String BoardTitle);
}
