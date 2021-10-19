package com.depromeet.breadmapbackend.bakeries.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BakeryMenuListResponse {

    private Long menuId;
    private Long breadCategoryId;
    private Long breadCategoryName;
    private String menuName;
    private Integer price;
    private String imgPath;
    @ApiModelProperty(value = "메뉴 별점(평균 점수), 별점 없을 경우 null 반환")
    private Float avgRating;
}

