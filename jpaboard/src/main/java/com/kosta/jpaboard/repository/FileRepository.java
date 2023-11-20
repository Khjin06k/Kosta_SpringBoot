package com.kosta.jpaboard.repository;

import com.kosta.jpaboard.dto.FileVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository  extends JpaRepository<FileVo, Integer> {
}
