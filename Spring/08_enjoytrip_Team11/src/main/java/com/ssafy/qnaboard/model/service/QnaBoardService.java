package com.ssafy.qnaboard.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.qnaboard.model.QnaArticleCommentDto;
import com.ssafy.qnaboard.model.QnaBoardDto;
import com.ssafy.qnaboard.model.QnaBoardListDto;

public interface QnaBoardService {
	public void writeArticle(QnaBoardDto qnaBoardDto) throws Exception;

	public QnaBoardDto getArticle(int qnaBoardId) throws Exception;

	public QnaBoardListDto listArticle(Map<String, String> map) throws Exception;

	public void updateHit(int articleId) throws Exception;

	void modifyArticle(QnaBoardDto qnaBoardDto) throws Exception;

	void removeArticle(int qnaBoardId) throws Exception;

	public List<QnaArticleCommentDto> listArticleComment(int qnaArticleId) throws Exception;

	public void writeArticleComment(QnaArticleCommentDto qnaArticleCommentDto) throws Exception;

	public void deleteArticleComment(int qnaArticleCommentId) throws Exception;

	public void modifyArticleComment(QnaArticleCommentDto qnaArticleCommentDto) throws Exception;
}
