package com.kosta.jpaboard.repository;

import com.kosta.jpaboard.entity.Board;
import com.kosta.jpaboard.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    Page<Board> findByTitleContains(String title, PageRequest pageRequest);
    Page<Board> findByContentContains(String content, PageRequest pageRequest);

}
