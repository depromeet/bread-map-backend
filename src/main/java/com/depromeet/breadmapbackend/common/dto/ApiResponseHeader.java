package com.depromeet.breadmapbackend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponseHeader {
    private int code;
    private String message;
}
