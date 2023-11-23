package com.ssafy.tripboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {
	private int tripArticleId;
	private String tagName;
	private int tagType;
}
