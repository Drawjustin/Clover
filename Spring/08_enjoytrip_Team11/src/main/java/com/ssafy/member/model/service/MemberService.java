package com.ssafy.member.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.MemberModifyDto;
import com.ssafy.tripboard.model.TripBoardDto;
import com.ssafy.tripboard.model.TripBoardListDto;

public interface MemberService {
	
	//회원등록
	void registerUser(MemberDto memberDto) throws Exception;
	
	//로그인
	MemberDto login(MemberDto memberDto);
	
	//회원수정
	MemberModifyDto modifyUser(MemberModifyDto memberModifyDto);
	
	//회원삭제
	void deleteUser(String userId);
	
	//비밀번호 찾기
	MemberDto findPassword(MemberDto memberDto);
	
//	//임시비밀번호 발급 후 수정
//	MemberDto modifyNewPass(MemberDto memberDto);
	
	//아이디 찾기
	MemberDto findUserId(MemberDto memberDto);
	
	//좋아요 누른 게시물 찾기
	TripBoardListDto selectLikeBoard(Map<String, String> map);
	
	//좋아요 누른 게시물 지우기
	void deleteLikeBoard(TripBoardDto tripboarddto);
	
	
	
	MemberDto userInfo(String userId) throws Exception;
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleRefreshToken(String userId) throws Exception;

	
}
