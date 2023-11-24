package com.ssafy.tripboard.model.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.tripboard.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TripBoardDao {

	void insertArticle(TripBoardDto tripBoardDto);

	void insertFile(TripBoardFileDto tripBoardFileDto);

	TripBoardDto selectArticle(int tripArticleId);

	List<TripBoardDto> selectArticleList(Map<String, Object> param);

	int getTotalArticleCount(Map<String, Object> param);

	void updateHit(int tripArticleId);

	void updateArticle(TripBoardDto tripBoardDto);

	void deleteArticle(int tripArticleId);

	List<TripArticleCommentDto> selectArticleCommentList(int tripArticleId);

	void insertArticleComment(TripArticleCommentDto tripArticleCommentDto);

	void deleteArticleComment(int tripArticleCommentId);

	void updateArticleComment(TripArticleCommentDto tripArticleCommentDto);

	void insertTag(List<TagDto> tagDtoList);

	TripBoardFileDto selectFile(int tripArticleId);

	void updateFile(TripBoardFileDto fileDto);

	void deleteTagsByArticleId(int tripArticleId);

//	int findClover(Map<String, Object> param);
}
