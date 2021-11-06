package com.depromeet.breadmapbackend.bakeries.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuListResponse {
    private List<String> menuList = new ArrayList<>();
}
