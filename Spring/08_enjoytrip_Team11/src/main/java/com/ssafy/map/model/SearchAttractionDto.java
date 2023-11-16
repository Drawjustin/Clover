package com.ssafy.map.model;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchAttractionDto {
	private int sidoCode;
	private int gugunCode;
	private int[] contentType;
	private String searchWord;
}
