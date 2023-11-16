package com.ssafy.qnaboard.model;

import lombok.Data;

@Data
public class QnaBoardComment {
	private int articleCommentId;
	private int articleNo;
	private String articleCommentContent;
	private String userId;
	private String registerTime;
	
	
}
