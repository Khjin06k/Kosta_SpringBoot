package com.kosta.jpaboard.service;

import com.kosta.jpaboard.dto.BoardDto;
import com.kosta.jpaboard.dto.FileVo;
import com.kosta.jpaboard.entity.Board;
import com.kosta.jpaboard.repository.BoardRepository;
import com.kosta.jpaboard.repository.FileRepository;
import com.kosta.jpaboard.util.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private FileRepository fileRepository;

    // 게시글 전체 조회
    @Override
    public List<BoardDto> boardListByPage(PageInfo pageInfo) throws Exception {
        // PageRequest 는 페이지 처리를 위해 제공하는 것, 전체 페이지, 가져올 페이지 수(10개씩), 정렬 방식(num을 기준으로 내림차순)
        PageRequest pageReqeust = PageRequest.of(pageInfo.getCurPage()-1, 10, Sort.by(Sort.Direction.DESC, "num")); // 페이지가 0부터 시작함
        Page<Board> pages = boardRepository.findAll(pageReqeust);

        pageInfo.setAllPage(pages.getTotalPages());
        int startPage = (pageInfo.getCurPage()-1)/10*10+1;
        int endPage = Math.min(startPage+10-1, pageInfo.getAllPage());
        pageInfo.setStartPage(startPage);
        pageInfo.setEndPage(endPage);

        List<BoardDto> boardDtoList = new ArrayList<>();
        for(Board board : pages.getContent()){
            boardDtoList.add(board.toBoardDto());
        }
       return boardDtoList;
    }

    // 게시글 1개 조회
    @Override
    public BoardDto boardDetail(Integer num) throws Exception {
        Optional<Board> oboard = boardRepository.findById(num);
        if(oboard.isEmpty()) throw new Exception("글 번호 오류");
        return oboard.get().toBoardDto();
    }

    // 조회수
    @Override
    public void plusViewCount(Integer num) throws Exception {
       Board board =  boardRepository.findById(num).get();
       board.setViewcount(board.getViewcount()+1);
       boardRepository.save(board);
    }

    //  업로드한 이미지 가져오기
    @Override
    public void readImage(Integer num, OutputStream out) throws Exception {
        String dir="/Users/gmlwls/Desktop/kosta/SpringBoot/upload/";
        FileInputStream fis = new FileInputStream(dir+num);
        FileCopyUtils.copy(fis, out);
        fis.close();
    }

    @Override
    public Integer boardWrite(BoardDto boardDto, List<MultipartFile> files) throws Exception {
        String dir = "/Users/gmlwls/Desktop/kosta/SpringBoot/upload/";
        if(files != null && !files.isEmpty()){
            // 파일 정보에 대한 DB 생성
            String fileNums="";
            for(MultipartFile file:files){
                FileVo fileVo = FileVo.builder()
                        .directory(dir)
                        .name(file.getOriginalFilename())
                        .size(file.getSize())
                        .contenttype(file.getContentType())
                        .data(file.getBytes())
                        .build();
                fileRepository.save(fileVo);

                // 업로드 폴더에 업로드
                File uploadFile = new File(dir+fileVo.getNum());
                file.transferTo(uploadFile);

                // file 번호 목록 만들기
                if(!fileNums.equals("")){
                    fileNums += ",";
                }
                fileNums += fileVo.getNum();
            }
            boardDto.setFileurl(fileNums);
        }
        Board board = boardDto.toEntity();
        boardRepository.save(board);

        return board.getNum();
    }

    @Override
    public Integer boardModify(BoardDto boardDto, List<MultipartFile> files) throws Exception {
        Board board = boardRepository.findById(boardDto.getNum()).get();
        board.setContent(boardDto.getContent());
        board.setTitle(boardDto.getTitle());
        String dir = "/Users/gmlwls/Desktop/kosta/SpringBoot/upload/";
        if(files != null && !files.isEmpty()){
            // 파일 정보에 대한 DB 생성
            String fileNums="";
            for(MultipartFile file : files){
                if(file.isEmpty()){
                    fileNums+=(fileNums.equals("")?"":",") + file.getOriginalFilename();
                }else {
                    FileVo fileVo = FileVo.builder()
                            .directory(dir)
                            .name(file.getOriginalFilename())
                            .size(file.getSize())
                            .contenttype(file.getContentType())
                            .data(file.getBytes())
                            .build();
                    fileRepository.save(fileVo);

                    // 업로드 폴더에 업로드
                    File uploadFile = new File(dir + fileVo.getNum());
                    file.transferTo(uploadFile);

                    // file 번호 목록 만들기
                    if (!fileNums.equals("")) {
                        fileNums += ",";
                    }
                    fileNums += fileVo.getNum();
                }
            }
            boardDto.setFileurl(fileNums);
        }else{
            board.setFileurl(null);
        }
        boardRepository.save(board);

        return board.getNum();
    }

    // 게시글 삭제
    @Override
    public void boardDelete(Integer num) throws Exception {

    }

    // 게시글 검색 기능

    @Override
    public List<BoardDto> searchBoard(PageInfo pageInfo, String type, String keyword) throws Exception {
        PageRequest pageRequest = PageRequest.of (pageInfo.getCurPage () -1, 10,
                Sort.by (Sort.Direction.DESC, "num"));

        Page<Board> pages = null;
        if(type.equals ("title")) {
            pages = boardRepository.findByTitleContains(keyword, pageRequest);
        } else if(type.equals("content")) {
            pages = boardRepository.findByContentContains(keyword, pageRequest);
        } else if(type.equals("writer")){
            pages = boardRepository.findByContentContains(keyword, pageRequest);
        } else{
            return null;
        }

        pageInfo.setAllPage(pages.getTotalPages());
        int startPage = (pageInfo. getCurPage () -1)/10*10+1;
        int endPage = Math.min(startPage+10-1, pageInfo.getAllPage ());
        pageInfo.setStartPage(startPage); pageInfo.setEndPage(endPage);
        pageInfo.setEndPage(endPage);
        List<BoardDto> boardDtoList = new ArrayList<>();
        for(Board board : pages.getContent()){
            boardDtoList.add(board.toBoardDto());
        }

        return boardDtoList;
    }
}
