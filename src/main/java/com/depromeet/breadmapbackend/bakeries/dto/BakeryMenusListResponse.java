package com.depromeet.breadmapbackend.bakeries.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BakeryMenusListResponse {

    private Long bakeryId;
    private Long menuId;
    private Long breadCategoryId;
    private Long breadCategoryName;
    private String menuName;
    private Integer price;
    private List<String> imgPathList;

}

