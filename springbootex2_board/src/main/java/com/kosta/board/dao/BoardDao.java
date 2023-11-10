package com.kosta.board.dao;

import com.kosta.board.dto.Board;
import com.kosta.board.dto.FileVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BoardDao {
    void insertBoard(Board board) throws Exception;
    List<Board> selectBoardList(Integer row) throws Exception;
    Integer selectBoardCount() throws Exception;

    Board selectBoard(Integer num) throws Exception;
    void updateBoard(Board board) throws Exception;
    void deleteBoard(Integer num) throws Exception;

    List<Board> searchBoardList(@Param("type") String type, @Param("keyword") String keyword, @Param("row") Integer row) throws Exception;
    Integer searchBoardCount(@Param("type") String type, @Param("keyword") String keyword) throws Exception;
    void updateBoardViewCount(Integer num) throws Exception;
    Integer selectLikeCount(Integer num) throws Exception;
    void plusBoardLikeCount(Integer num) throws Exception;
    void minusBoardListCount(Integer num) throws Exception;

    void insertFile(FileVo fileVo) throws Exception;
    FileVo selectFile(Integer num) throws Exception;
    void deleteFile(String num) throws Exception;
}
