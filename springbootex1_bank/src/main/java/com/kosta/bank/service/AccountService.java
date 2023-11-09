package com.kosta.bank.service;

import com.kosta.bank.dto.Account;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AccountService {
    void makeAccount(Account account) throws Exception;
    Account accountInfo(String id) throws Exception;
    Account plusBalance(String id, Integer balance) throws Exception;
    Account minusBalance(String id, Integer balance) throws Exception;
    List<Account> allAccountInfo() throws Exception;
}
