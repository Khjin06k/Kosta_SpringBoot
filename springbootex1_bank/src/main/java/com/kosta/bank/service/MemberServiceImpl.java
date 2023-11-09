package com.kosta.bank.service;

import com.kosta.bank.dao.MemberDao;
import com.kosta.bank.dto.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberDao memberDao;
    @Override
    public Member login(String id, String password) throws Exception {
        Member existMember = memberDao.selectMember(id);
        if(existMember == null){
            throw new Exception("아이디가 존재하지 않습니다.");
        }
        if(!existMember.getPassword().equals(password)){
            throw new Exception("비밀번호가 틀렸습니다");
        }
        return existMember;
    }

    @Override
    public void join(Member member) throws Exception {
        Member existMember = memberDao.selectMember(member.getId());
        if(existMember != null){
            throw new Exception("중복된 회원입니다.");
        }
        memberDao.insertMember(member);
    }
}
