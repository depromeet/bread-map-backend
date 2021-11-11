package com.depromeet.breadmapbackend.members.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo {

    private String profileImage;
    private String nickName;
}
