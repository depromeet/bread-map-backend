package com.depromeet.breadmapbackend.reviews.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMenuReviewsRequest {

    private Long bakeryId; // TODO PathVariable로 bakeryId를 받고 있어 body로 다시 받아야 할지 고려 필요
    private String categoryName;
    private String menuName;
    private Integer price;
    private Long rating;
    private String contents;
    private List<String> imgPathList;
}
