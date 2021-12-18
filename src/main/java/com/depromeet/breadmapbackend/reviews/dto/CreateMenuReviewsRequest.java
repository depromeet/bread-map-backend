package com.depromeet.breadmapbackend.reviews.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMenuReviewsRequest implements Serializable {

    private String categoryName;
    private String menuName;
    private Integer price;
    private Long rating;
    private String contents;
    private List<String> imgPathList;
}
