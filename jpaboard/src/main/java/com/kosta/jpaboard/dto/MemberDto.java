package com.kosta.jpaboard.dto;

import com.kosta.jpaboard.entity.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
	private String id;
	private String name;
	private String password;
	private String email;
	private String address;

	// Entity를 가지고 Dto를 생성
	public Member toEntity(){
		return Member.builder()
				.id(id)
				.name(name)
				.password(password)
				.email(email)
				.address(address)
				.build();
	}
}
