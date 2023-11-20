package com.kosta.jpaboard;

import com.kosta.jpaboard.entity.Board;
import com.kosta.jpaboard.entity.Member;
import com.kosta.jpaboard.repository.BoardRepository;
import com.kosta.jpaboard.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class JpaboardApplicationTests {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void contextLoads() {
    }
    @Test
    void selectBoard() {
        Board board = boardRepository.findById(12).get();
        System.out.println(board);
        System.out.println(board.getMember());
    }
    @Test
    void selectMember() {
        Member member = memberRepository.findById("hong").get();
        System.out.println(member);
        for(Board board : member.getBoardList()) {
            System.out.println(board);
        }
    }

    @Test // Fail
    void selectMemberByEmail(){
        List<Member> member = memberRepository.findByEmail("khj4209kk@gmail.com");
        System.out.println(member.toString());
    }

    @Test
    void selectBoardByWriter(){
        List<Board> board = boardRepository.findByWriter("hong");
        System.out.println(board.toString());
    }

}
