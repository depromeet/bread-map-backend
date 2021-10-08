package com.depromeet.breadmapbackend.auth.dto;

import com.depromeet.breadmapbackend.members.enumerate.MemberProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    private String accessToken;
}
