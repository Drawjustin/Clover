package com.ssafy.tripboard.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripBoardListDto {
	private List<TripBoardDto> articles;
	private int currentPage;
	private int totalPageCount;
}
