package com.ssafy.tripboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripBoardFileDto {
	private int tripBoardFileId;	//의미없는 id
	private int tripArticleId;		// 게시글 id foreign key로 받아온거
	private String saveFolder;		//저장할 폴더
	private String originalFile;	//원래 얘 이름
	private String saveFile;		// 저장될 유니크한 이름
}
