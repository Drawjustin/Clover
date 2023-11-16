package com.ssafy.qnaboard.model;

import lombok.Data;

@Data
public class QnaBoardDto {
	private int articleNo;
	private String userId;
	private String subject;
	private String content;
	private int hit;
	private String userName;
	private String registerTime;
}
