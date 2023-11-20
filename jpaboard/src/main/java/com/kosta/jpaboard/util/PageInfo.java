package com.kosta.jpaboard.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
	private Integer allPage;
	private Integer curPage;
	private Integer startPage;
	private Integer endPage;

	public PageInfo(Integer curPage) {
		super();
		this.curPage = curPage;
	}

}
