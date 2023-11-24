package com.ssafy.qnaboard.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.qnaboard.model.QnaArticleCommentDto;
import com.ssafy.qnaboard.model.QnaBoardDto;
import com.ssafy.qnaboard.model.QnaBoardListDto;

@Mapper
public interface QnaBoardDao {

	void insertArticle(QnaBoardDto qnaBoardDto) throws SQLException;

	QnaBoardDto selectArticle(int qnaBoardId) throws SQLException;

	List<QnaBoardDto> selectArticleList(Map<String, Object> param) throws SQLException;

	int getTotalArticleCount(Map<String, Object> param) throws SQLException;

	void updateHit(int articleId) throws SQLException;

	void updateArticle(QnaBoardDto qnaBoardDto) throws SQLException;

	void deleteArticle(int articleId) throws SQLException;

	List<QnaArticleCommentDto> selectArticleCommentList(int qnaArticleId) throws SQLException;

	void insertArticleComment(QnaArticleCommentDto qnaArticleCommentDto) throws SQLException;

	void deleteArticleComment(int qnaArticleCommentId) throws SQLException;

	void updateArticleComment(QnaArticleCommentDto qnaArticleCommentDto) throws SQLException;

}
