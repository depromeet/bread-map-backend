package com.depromeet.breadmapbackend.reviews.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MenuReviewListResponse {

    private Long reviewId;
    private Long bakeryId;
    private Long memberId;
    private String memberName;
    private String contents;
    private Integer rating;
    private List<String> imgPathList;
}
