package com.kosta.jpaboard.service;

import com.kosta.jpaboard.dto.BoardDto;
import com.kosta.jpaboard.entity.Board;
import com.kosta.jpaboard.util.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.List;

public interface BoardService {
    List<BoardDto> boardListByPage(PageInfo pageInfo) throws Exception;
    BoardDto boardDetail(Integer num) throws Exception;
    void plusViewCount(Integer num) throws Exception;
    void readImage(Integer num, OutputStream out) throws Exception;
    Integer boardWrite(BoardDto board, List<MultipartFile> multipartFile) throws Exception;
    Integer boardModify(BoardDto board, List<MultipartFile> multipartFile) throws Exception;
    void boardDelete(Integer num) throws Exception;

    List<BoardDto> searchBoard(PageInfo pageInfo, String type, String keyword) throws Exception;
}

