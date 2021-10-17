package com.depromeet.breadmapbackend.bakeries.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MenuListResponse {

    private List<String> menuList = new ArrayList<>();
}
