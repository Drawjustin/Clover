package com.ssafy.tripboard.model;

import lombok.Data;

@Data
public class TripBoardDto {
	private int tripArticleId;
	private String userId;
	private String userName;
	private String registerTime;
	private int totalClover;
	private String subject;
	private String content;
	private int hit;
	private int cost;
	
}
