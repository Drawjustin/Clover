package com.ssafy.member.model;

import lombok.Data;

@Data
public class MemberModifyDto {

	private String userId;
	private String userName;
	private String userPass;
	private String userEmail;
}
