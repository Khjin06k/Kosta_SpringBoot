package com.kosta.bank.dao;

import com.kosta.bank.dto.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// Account.xml에서 경로를 직접 Dao로 설정하였기 때문에 따로 Impl이 없어도 이름으로 가져와서 사용이 가능함
@Mapper
@Repository
public interface AccountDao {
    void insertAccount(Account account) throws Exception;
    Account selectAccount(String id) throws Exception;
    void updateBalance (@Param("id") String id, @Param("balance") Integer balance) throws Exception;
    List<Account> selectAccountList() throws Exception;
}
