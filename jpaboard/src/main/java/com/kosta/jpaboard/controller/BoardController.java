package com.kosta.jpaboard.controller;

import com.kosta.jpaboard.dto.BoardDto;
import com.kosta.jpaboard.entity.Board;
import com.kosta.jpaboard.service.BoardService;
import com.kosta.jpaboard.util.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 전체 페이지 조회
    @GetMapping("/{page}")
    public ResponseEntity<Object> boardList(@PathVariable(required = false) Integer page){
        // 조회를 해서 보내줄 때 리스트 뿐만 아니라 페이지 정보까지 함께 보내주어야 함
        try{
            /*PageInfo pageInfo = new PageInfo();
            System.out.println("page = " + page);
            pageInfo.setCurPage(page);*/
            // 위 두줄을 생성자를 사용해서 한 줄의 코드로 변경 가능
            PageInfo pageInfo = new PageInfo(page);

            // 전체 계시판 조회
            List<BoardDto> boardList = boardService.boardListByPage(pageInfo);

            // 반환할 Map 생성 및 반환값 지정
            Map<String, Object> param = new HashMap<>();
            param.put("boardList", boardList);
            param.put("pageInfo", pageInfo); // page는 Service에서 set이 되어 있기 때문에 따로 정보를 가져올 필요 없이 그냥 넣으면 됨

            return new ResponseEntity<>(param, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("게시글 조회 불가",HttpStatus.NOT_FOUND);
        }
    }

    // 검색 기능
    @GetMapping("/search/{page}/{type}/{keyword}")
    public ResponseEntity<Object> search(@PathVariable Integer page, @PathVariable String type, @PathVariable String keyword){
        try{
            PageInfo pageInfo = new PageInfo(page);
            List<BoardDto> searchBoardList = boardService.searchBoard(pageInfo, type, keyword);

            // 반환할 Map 생성 및 반환값 지정
            Map<String, Object> param = new HashMap<>();
            param.put("8", searchBoardList);
            param.put("pageInfo", pageInfo); // page는 Service에서 set이 되어 있기 때문에 따로 정보를 가져올 필요 없이 그냥 넣으면 됨

            return new ResponseEntity<>(param, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("검색 불가",HttpStatus.NOT_FOUND);
        }
    }

    // 게시글 1개 조회
    @GetMapping("/boardDetail/{num}")
    public ResponseEntity<Object> boardOne(@PathVariable Integer num){
        try{
            BoardDto board = boardService.boardDetail(num);
            return new ResponseEntity<>(board, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("게시글 조회 불가",HttpStatus.NOT_FOUND);
        }
    }

    // 이미지 조회
    @GetMapping("/image/{fileUrl}")
    public void imageView(@PathVariable Integer fileUrl, HttpServletResponse response){
        try{
            boardService.readImage(fileUrl, response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 게시글 삭제
    @DeleteMapping("/boardDelete/{num}")
    public ResponseEntity<Integer> boardDelete(@PathVariable Integer num){
        try{
            boardService.boardDelete(num);
            return new ResponseEntity<>(num, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 게시글 등록
    @PostMapping("/register")
    public ResponseEntity<Integer> register(@ModelAttribute BoardDto board, List<MultipartFile> file){
        // @ModelAttribute를 사용할 때에는 프론트에서 form 객체를 만들어서 가져와야 함
        try{
            Integer boardNum = boardService.boardWrite(board, file);
            return new ResponseEntity<>(boardNum, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 게시글 수정
    @PutMapping("/modify")
    public ResponseEntity<String> modify(@ModelAttribute BoardDto board,List<MultipartFile> file){
        // @ModelAttribute를 사용할 때에는 프론트에서 form 객체를 만들어서 가져와야 함
        try{
            System.out.println(board.getTitle());
            boardService.boardModify(board, file);
            System.out.println(board.getTitle());
            return new ResponseEntity<>("true", HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
