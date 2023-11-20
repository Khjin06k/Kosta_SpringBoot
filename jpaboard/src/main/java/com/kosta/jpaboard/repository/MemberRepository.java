package com.kosta.jpaboard.repository;

import com.kosta.jpaboard.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    List<Member> findByEmail(String emaiil);
}
