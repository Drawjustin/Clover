package com.ssafy.member.model.service;

import com.ssafy.member.model.MemberDto;

public interface MemberService {
	//회원등록
	void registerUser(MemberDto memberDto);
	//로그인
	MemberDto login(String user_id, String user_pass);
	//회원수정
	MemberDto modifyUser(String user_id, String user_name, String user_pass);
	//회원삭제
	void deleteUer(String user_id);
	//비밀번호 찾기
	MemberDto findPassword(MemberDto memberDto);
	//아이디 찾기
	MemberDto findUserId(String user_id);
}
