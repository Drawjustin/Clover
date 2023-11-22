package com.ssafy.member.model;

import lombok.Data;

@Data
public class MemberDto {
	private String userId;
	private String userName;
	private String userPass;
	private String joinDate;
	private String userEmail;
	private String token;
	private String userQuestionResult;
	
}
