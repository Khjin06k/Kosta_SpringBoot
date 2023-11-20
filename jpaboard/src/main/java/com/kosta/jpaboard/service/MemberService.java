package com.kosta.jpaboard.service;


import com.kosta.jpaboard.dto.MemberDto;

public interface MemberService {
    MemberDto login(String id, String password) throws Exception;

    void join(MemberDto member) throws Exception;
}
