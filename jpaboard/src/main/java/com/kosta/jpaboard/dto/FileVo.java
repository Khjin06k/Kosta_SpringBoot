package com.kosta.jpaboard.dto;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name="FILE") // 테이블 명 지정
public class FileVo {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer num;
	@Column
	private String directory;
	@Column
	private String name;
	@Column
	private Long size;
	@Column
	private String contenttype;
	@Column
	private Date uploaddate;
	@Column
	private byte[] data;
}
