package com.ssafy.member.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.MemberModifyDto;
import com.ssafy.tripboard.model.TripBoardDto;

//@Mapper
public interface MemberDao {
	
	//회원등록
	void insertUser(MemberDto memberDto);
	
	//로그인
	MemberDto login(MemberDto memberDto);
	
	//회원수정
	int updateUser(MemberModifyDto memberModifyDto);
	
	//회원삭제
	void deleteUser(String userId);
	
	//비밀번호 찾기
	MemberDto selectFindPass(MemberDto memberDto);

	//임시비밀번호
	int updateNewPass(MemberDto memberDto);
	
	//아이디 찾기
	MemberDto selectFindId(MemberDto memberDto);
	
	//좋아요 누른 게시물 찾기
	List<TripBoardDto> selectLikeBoard(Map<String, Object> map);
	
	//좋아요 누른 게시물 지우기
	void deleteLikeBoard(TripBoardDto tripboarddto);
	
	int getTotalArticleCount(Map<String, Object> param);
	
	
	MemberDto userInfo(String userId) throws SQLException;
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String userid) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;

	
	

}
