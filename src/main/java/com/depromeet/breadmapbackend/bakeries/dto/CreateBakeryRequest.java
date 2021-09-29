package com.depromeet.breadmapbackend.bakeries.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBakeryRequest {

    private String breadShopName;
    private Double latitude;
    private Double longitude;
    private String address;
    private List<String> exteriorImgPathList;
    private List<String> interiorImgPathList;
}
