package com.kosta.bank.controller;

import com.kosta.bank.dto.Member;
import com.kosta.bank.service.MemberService;
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

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String id, @RequestParam String password,
                        Model model){
        try{
            Member member = memberService.login(id, password);
            if(member != null) session.setAttribute("member", member);
            return "makeaccount";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("err", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/join")
    public String join(){
        return "join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute Member member, Model model){
        try{
            memberService.join(member);
            // redirect를 설정해주지 않으면 회원가입과 동시에 로그인이 되는 현상 발생
            // 이는 세션에 member가 있는 것이 아닌 요청값에 member가 포함된 값으로,
            // 로그인 페이지에서 헤더를 포워딩하고 있는데 세션의 회원 또한 member이기 때문에 세션의 member가 null 이더라도
            // request에서 받아오는 member가 존재하기 때문에 member가 존재하는 것이 되어버림
            // 따라서 request에서도 member를 없애주기 위해서 redirect를 해 주어야 로그인 페이지로 넘어갔을 때
            // session/request에 모두 member가 없게 되는 것이고,
            return "redirect:/login";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("err", e.getMessage());
            return "redirect:/error";
        }
    }

    @GetMapping("/logout")
    public String logOut(){
        session.removeAttribute("member");
        return "login";
    }
}
