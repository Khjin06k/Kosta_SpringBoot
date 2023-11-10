package com.kosta.board.service;

import com.kosta.board.dto.Board;
import com.kosta.board.util.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.List;

public interface BoardService {
    List<Board> boardListByPage(PageInfo pageInfo) throws Exception;
    Board boardDetail(Integer num) throws Exception;
    void readImage(Integer num, OutputStream out) throws Exception;
    void boardWrite(Board board, MultipartFile multipartFile) throws Exception;
    void boardModify(Board board, MultipartFile file) throws Exception;
    void boardDelete(Integer num) throws Exception;

    List<Board> searchBoard(PageInfo pageInfo, String type, String keyword) throws Exception;
}
