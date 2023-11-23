package com.ssafy.tripboard.model;

import java.util.List;

import lombok.Data;

@Data
public class TripBoardListDto {
	private List<TripBoardDto> triparticles;
	private int currentPage;
	private int totalPageCount;
}
