package com.kosta.jpaboard.dto;

import com.kosta.jpaboard.entity.Board;
import com.kosta.jpaboard.entity.Member;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
	private Integer num;
	private String title;
	private String content;
	private Date writedate;
	private String fileurl;
	private String writer;
	private Integer viewcount;
	private Integer likecount;
	private String writename;

	public Board toEntity(){
		return Board.builder()
				.num(num)
				.title(title)
				.content(content)
				.fileurl(fileurl)
				.writedate(writedate)
				.viewcount(0)
				.likecount(0)
				.member(Member.builder().id(writer).build()) // id만 가지는 member 객체를 만들어서 넣어줌
				.build();
	}
}
