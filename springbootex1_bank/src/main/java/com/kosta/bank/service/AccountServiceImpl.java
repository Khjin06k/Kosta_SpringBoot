package com.kosta.bank.service;

import com.kosta.bank.dao.AccountDao;
import com.kosta.bank.dto.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountDao accountDao;

    @Override
    public void makeAccount(Account account) throws Exception {
        Account sacc = accountInfo(account.getId());
        if(sacc!=null) throw new Exception("계좌번호 중복");
        accountDao.insertAccount(account);
    }

    @Override
    public Account accountInfo(String id) throws Exception {
        return accountDao.selectAccount(id);
    }

    @Override
    public Account plusBalance(String id, Integer balance) throws Exception {
        Account account = accountInfo(id);
        Integer change = account.getBalance() + balance;
        accountDao.updateBalance(id, change);
        return accountInfo(id);
    }

    @Override
    public Account minusBalance(String id, Integer balance) throws Exception {
        Account account = accountInfo(id);
        Integer change = account.getBalance() - balance;
        accountDao.updateBalance(id, change);
        return accountInfo(id);
    }

    @Override
    public List<Account> allAccountInfo() throws Exception {
        return accountDao.selectAccountList();
    }
}
