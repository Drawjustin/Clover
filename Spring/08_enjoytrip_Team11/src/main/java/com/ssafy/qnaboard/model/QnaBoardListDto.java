package com.ssafy.qnaboard.model;

import java.util.List;

import lombok.Data;

@Data
public class QnaBoardListDto {
	private List<QnaBoardDto> articles;
	private int currentPage;
	private int totalPageCount;
}
