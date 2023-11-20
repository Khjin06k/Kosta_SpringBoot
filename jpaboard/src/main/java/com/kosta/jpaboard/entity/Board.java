package com.kosta.jpaboard.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.kosta.jpaboard.dto.BoardDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class Board {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer num;
	@Column
	private String title;
	@Column
	private String content;
	@Column
	private String fileurl;
	@Column
	private Integer viewcount;
	@Column
	private Integer likecount;

	//@CreatedDate : 왜 안되는지 이유 알고싶,,,
	@Column
	@CreationTimestamp
	private Date writedate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="writer")
	private Member member;

	public BoardDto toBoardDto(){
		return BoardDto.builder()
				.num(num)
				.title(title)
				.content(content)
				.fileurl(fileurl)
				.writer(member.getId())
				.writedate(writedate)
				.viewcount(viewcount)
				.likecount(likecount)
				.writename(member.getName())
				.build();
	}

	/*@Override
	public String toString() {
		return String.format("[%d,%s,%s,%s,%s]", num,title,content,fileurl,member.getId());
	}
*/
}
