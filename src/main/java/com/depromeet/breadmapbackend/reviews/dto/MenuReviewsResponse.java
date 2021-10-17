package com.depromeet.breadmapbackend.reviews.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MenuReviewsResponse {

    private Long menuReviewId;
    private Long memberId;
    private String memberName;
    private Long menuId;
    private List<String> imagePathList;
    private String contents;
    private Integer rating;
}
