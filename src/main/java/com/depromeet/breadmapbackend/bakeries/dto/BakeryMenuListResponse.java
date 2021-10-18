package com.depromeet.breadmapbackend.bakeries.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BakeryMenuListResponse {

    private Long bakeryId;
    private Long menuId;
    private Long breadCategoryId;
    private Long breadCategoryName;
    private String menuName;
    private Integer price;
    private String imgPath;
    @ApiModelProperty(value = "메뉴 별점(평균 점수)")
    private Float avgRating;
}

