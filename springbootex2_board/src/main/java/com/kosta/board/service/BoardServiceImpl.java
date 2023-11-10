package com.kosta.board.service;

import com.kosta.board.dao.BoardDao;
import com.kosta.board.dto.Board;
import com.kosta.board.dto.FileVo;
import com.kosta.board.util.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardDao boardDao;

    // 게시글 전체 조회
    @Override
    public List<Board> boardListByPage(PageInfo pageInfo) throws Exception {
        int boardCount = boardDao.selectBoardCount();
        if(boardCount == 0) return null;

        int allPage = (int)Math.ceil((double)boardCount/10);
        int startPage = (pageInfo.getCurPage()-1)/10*10+1;
        int endPage = Math.min(startPage+10-1, allPage);

        pageInfo.setAllPage(allPage);
        pageInfo.setStartPage(startPage);
        pageInfo.setEndPage(endPage);
        if(pageInfo.getCurPage()>allPage) pageInfo.setCurPage(allPage);

        int row = (pageInfo.getCurPage()-1)*10+1;
        return boardDao.selectBoardList(row-1);
    }

    // 게시글 1개 조회
    @Override
    public Board boardDetail(Integer num) throws Exception {
        Board board = boardDao.selectBoard(num);
        if(board==null) throw new Exception("글 번호 오류");

        return board;
    }

    //  업로드한 이미지 가져오기
    @Override
    public void readImage(Integer num, OutputStream out) throws Exception {
        String dir = "/Users/gmlwls/Desktop/kosta/SpringBoot/upload/";
        FileInputStream fis = new FileInputStream(dir+num);
        FileCopyUtils.copy(fis, out);
        fis.close();
    }

    @Override
    public void boardWrite(Board board, MultipartFile file) throws Exception {
        if(file != null && !file.isEmpty()){
            // 파일 정보에 대한 DB 생성
            String dir = "/Users/gmlwls/Desktop/kosta/SpringBoot/upload/";
            FileVo fileVo = new FileVo();
            fileVo.setDirectory(dir);
            fileVo.setName(file.getOriginalFilename());
            fileVo.setSize(file.getSize());
            fileVo.setContenttype(file.getContentType());
            fileVo.setData(file.getBytes());
            boardDao.insertFile(fileVo);

            File uploadFile = new File(dir+fileVo.getNum());
            file.transferTo(uploadFile);
            board.setFileurl(fileVo.getNum()+"");

        }
        boardDao.insertBoard(board);
    }

    @Override
    public void boardModify(Board board, MultipartFile file) throws Exception {
        if(file != null && !file.isEmpty()){
            // 파일 정보에 대한 DB 생성
            String dir = "/Users/gmlwls/Desktop/kosta/SpringBoot/upload/";
            FileVo fileVo = new FileVo();
            fileVo.setDirectory(dir);
            fileVo.setName(file.getOriginalFilename());
            fileVo.setSize(file.getSize());
            fileVo.setContenttype(file.getContentType());
            fileVo.setData(file.getBytes());
            boardDao.insertFile(fileVo);

            // 파일 업로드
            File uploadFile = new File(dir+fileVo.getNum());
            file.transferTo(uploadFile);
            board.setFileurl(fileVo.getNum()+"");

            // 기존 파일이 있으면 삭제
            Board sboard = boardDao.selectBoard(board.getNum());
            String orgFileNum = sboard.getFileurl();
            if(orgFileNum!=null){
                System.out.println(orgFileNum);

                boardDao.deleteFile(orgFileNum); // 테이블에서 삭제

                File orgFile = new File(dir+orgFileNum); // 업로드 폴더에서 제거
                if(orgFile.exists()){
                    orgFile.delete();
                }
            }
        }
        boardDao.updateBoard(board);
    }

    // 게시글 삭제

    @Override
    public void boardDelete(Integer num) throws Exception {
        Board board = boardDao.selectBoard(num);
        if(board == null) throw new Exception("삭제할 게시글이 없습니다.");
        if(board.getFileurl() != null && !board.getFileurl().equals("")){
            boardDao.deleteFile(board.getFileurl());
            String dir = "/Users/gmlwls/Desktop/kosta/SpringBoot/upload/";
            File file = new File(dir+board.getFileurl());
            if(file.exists()) file.delete();
        }
        boardDao.deleteBoard(num);
    }

    // 게시글 검색 기능

    @Override
    public List<Board> searchBoard(PageInfo pageInfo, String type, String keyword) throws Exception {
        int boardCount = boardDao.searchBoardCount(type, keyword);
        if(boardCount == 0) return null;

        int allPage = (int)Math.ceil((double)boardCount/10);
        int startPage = (pageInfo.getCurPage()-1)/10*10+1;
        int endPage = Math.min(startPage+10-1, allPage);

        pageInfo.setAllPage(allPage);
        pageInfo.setStartPage(startPage);
        pageInfo.setEndPage(endPage);
        if(pageInfo.getCurPage()>allPage) pageInfo.setCurPage(allPage);

        int row = (pageInfo.getCurPage()-1)*10+1;
        return boardDao.searchBoardList(type, keyword, row-1);
    }
}
