package com.depromeet.breadmapbackend.members.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {

    private String profileImage;
    private String nickName;
    private String email;
}
