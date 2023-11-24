package com.ssafy.tripboard.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.tripboard.model.TripArticleCommentDto;
import com.ssafy.tripboard.model.TripBoardDto;
import com.ssafy.tripboard.model.TripBoardListDto;

public interface TripBoardService {

	void writeArticle(TripBoardDto tripBoardDto) throws Exception;

	TripBoardDto getArticle(int tripArticleId) throws Exception;

	void updateHit(int tripArticleId) throws Exception;

	TripBoardListDto listArticle(Map<String, String> map) throws Exception;

	void removeArticle(int tripArticleId) throws Exception;

	List<TripArticleCommentDto> listArticleComment(int tripArticleId) throws Exception;

	void modifyArticle(TripBoardDto tripBoardDto) throws Exception;

	void modifyArticleComment(TripArticleCommentDto tripArticleCommentDto) throws Exception;

	void deleteArticleComment(int tripArticleCommentId) throws Exception;

	void writeArticleComment(TripArticleCommentDto tripArticleCommentDto) throws Exception;

}
