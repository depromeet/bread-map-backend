package com.depromeet.breadmapbackend.bakeries.dto;


import com.depromeet.breadmapbackend.common.enumerate.BreadCategoryType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BakeryMenuResponse {

    private Long menuId;
    private Long breadCategoryId;
    private BreadCategoryType breadCategoryName;
    private String menuName;
    private Integer price;
    private String imgPath;
    @ApiModelProperty(value = "메뉴 별점(평균 점수), 별점 없을 경우 null 반환")
    private Double avgRating;

    @Builder
    public BakeryMenuResponse(BakeryMenuResponse bakeryMenuResponse) {
        this.menuId = bakeryMenuResponse.getMenuId();
        this.breadCategoryId = bakeryMenuResponse.getBreadCategoryId();
        this.breadCategoryName = bakeryMenuResponse.getBreadCategoryName();
        this.menuName = bakeryMenuResponse.getMenuName();
        this.price = bakeryMenuResponse.getPrice();
        this.imgPath = bakeryMenuResponse.getImgPath();
        this.avgRating = bakeryMenuResponse.getAvgRating();
    }
}

