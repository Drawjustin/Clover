package com.ssafy.tripboard.model;

import lombok.Data;

@Data
public class TripBoardFileDto {
	private int tripBoardFileId;
	private int tripBoardNo;
	private String saveFolder;
	private String originalFile;
	private String saveFile;
	
}
