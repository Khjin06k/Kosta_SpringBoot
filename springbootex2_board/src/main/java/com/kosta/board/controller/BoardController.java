package com.kosta.board.controller;

import com.kosta.board.dto.Board;
import com.kosta.board.service.BoardService;
import com.kosta.board.util.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping
    public String main() {
        return "main";
    }

    // 전체 게시글 조회
    @GetMapping({"/boardlist/{page}", "/boardlist"})
    public String BoardList(@PathVariable(required = false) Integer page, Model model) {
        PageInfo pageInfo = new PageInfo();
        if (page == null) pageInfo.setCurPage(1);
        else pageInfo.setCurPage(page);

        try {
            List<Board> boardList = boardService.boardListByPage(pageInfo);
            model.addAttribute("pageInfo", pageInfo);
            model.addAttribute("boardList", boardList);
            return "boardlist";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("err", "게시글 목록 조회 오류");
            return "error";
        }
    }

    // 게시글 1개 조회(디테일 페이지)
    @GetMapping("/boarddetail/{num}/{page}")
    public String boardDetail(@PathVariable("num") Integer num, @PathVariable("page") Integer page, Model model) {
        try {
            Board board = boardService.boardDetail(num);
            model.addAttribute("board", board);
            model.addAttribute("page", page);
            return "detailform";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("err", "게시글 조회 조회 오류");
            return "error";
        }

    }

    // 게시글 작성 페이지 접속
    @GetMapping("/boardwrite")
    public String boardWrite() {
        return "writeform";
    }

    // 작성 게시글 등록
    @PostMapping("/boardwrite")
    public String boardWrite(@ModelAttribute Board board, MultipartFile file, Model model) {
        try {
            boardService.boardWrite(board, file);
            boardService.boardDetail(board.getNum());

            return "detailform";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("err", "게시글 등록 오류");
            return "error";
        }
    }

    // 이미지 url 가져오기
    @GetMapping("/image/{num}")
    public void imageView(@PathVariable("num") Integer num, HttpServletResponse response) {
        try {
            boardService.readImage(num, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 게시글 수정
    @GetMapping("/boardmodify/{num}/{page}")
    public String boardModify(@PathVariable("num") Integer num, @PathVariable("page") Integer page,
                              Model model) {
        try {
            Board board = boardService.boardDetail(num);
            model.addAttribute("board", board);
            return "modifyform";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("err", "글 수정 불가");
            return "error";
        }
    }

    // 게시글 수정 로직
    @PostMapping("/boardmodify")
    public String boardModify(@ModelAttribute Board board, MultipartFile file,
                              @RequestParam("page") Integer page, Model model) {

        try {
            boardService.boardModify(board, file);
            return "detailform";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("err", "글 수정 불가");
            return "error";
        }
    }

    // 게시글 삭제
    @GetMapping("/boarddelete/{num}/{page}")
    public String boardDelete(@PathVariable("num") Integer num, @PathVariable("page") Integer page,
                              Model model) {
        try {
            boardService.boardDelete(num);
            return "redirect:/boardlist/" + page;
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("err", "글 삭제 불가");
            return "error";
        }
    }

    // 게시글 검색
    @PostMapping("/boardsearch")
    public String boardSearch(@RequestParam("type") String type, @RequestParam("keyword") String keyword,
                              Model model, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        try {
            PageInfo pageInfo = new PageInfo();
            pageInfo.setCurPage(page);

            List<Board> boardList = boardService.searchBoard(pageInfo, type, keyword);
            model.addAttribute("pageInfo", pageInfo);
            model.addAttribute("boardList", boardList);
            model.addAttribute("keyword", keyword);
            model.addAttribute("type", type);
            return "boardlist";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("err", "글 검색 불가");
            return "error";
        }
    }
}

