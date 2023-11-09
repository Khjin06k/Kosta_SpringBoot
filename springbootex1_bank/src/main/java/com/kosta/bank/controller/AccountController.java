package com.kosta.bank.controller;

import com.kosta.bank.dto.Account;
import com.kosta.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AccountController {
    // 의존성 주입 - 필드주입
    @Autowired
    private AccountService accountService;

    @GetMapping
    public String main(){
        return "main";
    }

    // 게좌번호 페이지 조회
    @GetMapping("/makeaccount")
    public String makeAccount(){
        return "makeaccount";
    }

    // 계좌번호 개설
    @PostMapping("/makeaccount")
    public String makeAccount(@ModelAttribute Account account, Model model){
        try{
            accountService.makeAccount(account);
            Account saccount = accountService.accountInfo(account.getId());
            model.addAttribute("acc", saccount);
            return "accountinfo";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("err", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/deposit")
    public String deposit(){
        return "deposit";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam String id, @RequestParam Integer balance,
                          Model model){
        try{
            Account account = accountService.plusBalance(id, balance);
            model.addAttribute("acc", account);
            return "accountinfo";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("err", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/withdraw")
    public String withdraw(){
        return "withdraw";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam String id, @RequestParam Integer balance,
                          Model model){
        try{
            Account account = accountService.minusBalance(id, balance);
            model.addAttribute("acc", account);
            return "accountinfo";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("err", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/accountinfo")
    public String accountInfo(){
        return "accountinfoform";
    }

    @PostMapping("/accountinfo")
    public String accountInfo(@RequestParam String id, Model model){
        try{
            Account account = accountService.accountInfo(id);
            model.addAttribute("acc", account);
            return "accountinfo";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("err", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/allaccountinfo")
    public String allAccountInfo(Model model){
        try{
            List<Account> allAccountList = accountService.allAccountInfo();
            model.addAttribute("accs", allAccountList);
            return "allaccountinfo";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("err", e.getMessage());
            return "error";
        }
    }
}
