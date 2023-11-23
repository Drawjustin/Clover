package com.ssafy.tripboard.model;

import lombok.Data;

@Data
public class TripArticleCommentDto {
    private int tripArticleCommentId;
    private int tripArticleId;
    private String tripArticleCommentContent;
    private String userId;
    private String registerTime;
    private String userName;
}
