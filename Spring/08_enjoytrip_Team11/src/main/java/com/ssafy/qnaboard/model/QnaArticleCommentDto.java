package com.ssafy.qnaboard.model;

import lombok.Data;

@Data
public class QnaArticleCommentDto {
	private int qnaArticleCommentId;
	private int qnaArticleId;
	private String qnaArticleCommentContent;
	private String userId;
	private String registerTime;
	private String userName;
}
