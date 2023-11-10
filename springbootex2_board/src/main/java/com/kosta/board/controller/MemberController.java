package com.kosta.board.controller;

import com.kosta.board.dto.Member;
import com.kosta.board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private HttpSession session;

    // 로그인 페이지 접속
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    // 로그인 로직
    @PostMapping("/login")
    public String login(@RequestParam String id, @RequestParam String password, Model model){
        try{
            Member member = memberService.login(id, password);
            session.setAttribute("user", member);
            return "main";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("err", e.getMessage());
            return "error";
        }
    }

    // 회원가입 페이지 접속
    @GetMapping("join")
    public String join(){
        return "join";
    }

    // 회원가입 로직
    @PostMapping("/join")
    public String join(@ModelAttribute Member member, Model model){
        try{
            memberService.join(member);
            return "redirect:/login";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("err", e.getMessage());
            return "error";
        }
    }

    // 로그아웃 로직
    @GetMapping("/logout")
    public String logout(){
        session.removeAttribute("user");
        return "login";
    }
}
