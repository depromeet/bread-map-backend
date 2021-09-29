package com.depromeet.breadmapbackend.breadShops.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BakeryDetailResponse {

    private Long breadShopId;
    private String breadShopName;
    private String address;
    private Integer flagsCount;
    private List<String> exteriorImgPathList;
    private List<String> interiorImgPathList;
}
