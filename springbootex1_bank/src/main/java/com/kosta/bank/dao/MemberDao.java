package com.kosta.bank.dao;

import com.kosta.bank.dto.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberDao {
    Member selectMember(String id) throws Exception;
    void insertMember(Member member) throws Exception;
}