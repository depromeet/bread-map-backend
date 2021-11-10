package com.depromeet.breadmapbackend.members.controller;

import com.depromeet.breadmapbackend.auth.jwt.JwtHeaderUtil;
import com.depromeet.breadmapbackend.common.dto.ApiResponse;
import com.depromeet.breadmapbackend.members.dto.UserInfoResponse;
import com.depromeet.breadmapbackend.members.service.MembersService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {

    private final MembersService membersService;

    /**
     * 유저 정보 조회
     * @param HttpServletRequest
     * @return ResponseEntity<UserInfoResponse>
     */
    @ApiOperation(value = "로그인한 유저의 정보 조회", notes = "로그인한 유저의 정보 조회")
    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> getUserInfo(HttpServletRequest request) {
        String token = JwtHeaderUtil.getAccessToken(request);
        return ApiResponse.success(membersService.getUserInfo(token));
    }
}
