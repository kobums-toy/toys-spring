package com.example.toysspring.service;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;

import com.example.toysspring.domain.Board;
import com.example.toysspring.domain.BoardRepository;

@Service
public class BoardService {

    @Autowired
    BoardRepository repository;

    @Transactional
    public Board insert(Board item) {
        return repository.save(item);
    }

    @Transactional
    public Board update(Board item) {
        return repository.save(item);
    }

    @Transactional
    public void delete(Board item) {
        repository.delete(item);
    }

    public Optional<Board> findById(Long boardId) {
        return repository.findByBoardId(boardId);
    }

    public Page<Board> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("modelNo").descending());

        return repository.findAll(pageable);
    }

    public List<Board> findAll() {
        return repository.findAll();
    }
}
