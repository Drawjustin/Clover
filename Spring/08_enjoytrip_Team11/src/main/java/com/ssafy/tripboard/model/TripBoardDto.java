package com.ssafy.tripboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private TripBoardFileDto tripBoardFileDto;
    private List<TagDto> tags;
}
