package com.depromeet.breadmapbackend.filters.dto;

import com.depromeet.breadmapbackend.common.enumerate.FilterType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FilterDto {

    private Long filterId;
    private FilterType filterType;
    private String filterName;
}
