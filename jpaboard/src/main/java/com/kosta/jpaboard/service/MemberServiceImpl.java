package com.kosta.jpaboard.service;

import com.kosta.jpaboard.dto.MemberDto;
import com.kosta.jpaboard.entity.Member;
import com.kosta.jpaboard.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public MemberDto login(String id, String password) throws Exception {
        Optional<Member> omember = memberRepository.findById(id);
        if(omember.isEmpty()) throw new Exception("존재하지 않은 회원");
        Member member = omember.get();
        if(!member.getPassword().equals(password)) throw new Exception("비밀번호 오류");
        return member.toDto(); // member를 Dto로 만들어서 리턴
    }

    @Override
    public void join(MemberDto memberDto) throws Exception {
        Optional<Member> omember = memberRepository.findById(memberDto.getId());
        if(omember.isEmpty()) throw new Exception("아이디 중복 오류");
        // Dto를 Entity로 만들어 주는 것 (Dto 자체가 자기 변수를 복사해서 Entity를 만들어 주기 때문에 static이 아니어도 됨)
        Member member = memberDto.toEntity();
        memberRepository.save(member);
    }
}
