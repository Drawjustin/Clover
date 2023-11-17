package com.ssafy.map.model;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttractionDetailDto {

	private int contentId;
	private String cat1;
	private String cat2;
	private String cat3;
	private String createdTime;
	private String modifiedTime;
	private String booktour;

}
