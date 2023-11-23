package com.ssafy.member.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
public class MemberModifyDto {
	private String userId;
	private String userName;
	private String userPass;
	private String userEmail;
	private String userQuestionResult;

}
