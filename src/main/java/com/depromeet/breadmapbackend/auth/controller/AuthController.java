package com.depromeet.breadmapbackend.auth.controller;

import com.depromeet.breadmapbackend.auth.dto.AuthRequest;
import com.depromeet.breadmapbackend.auth.dto.AuthResponse;
import com.depromeet.breadmapbackend.common.dto.ApiResponse;
import com.depromeet.breadmapbackend.members.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final static String REFRESH_TOKEN = "refresh_token";
    private final MemberService memberService;

    /**
     * 사용자 로그인 기능
     * @return ResponseEntity<AuthResponse>
     */
    @ApiOperation(value = "카카오 로그인", notes = "카카오 엑세스 토큰을 이용하여 사용자 정보 받아 저장하고 앱의 토큰 반환")
    @PostMapping(value = "/kakao")
    public ResponseEntity<AuthResponse> kakaoAuthRequest(@RequestBody AuthRequest authRequest) {
        return ApiResponse.created(memberService.login(authRequest));
    }


    /**
     * 개발예정
     */
//    @GetMapping("/refresh")
//    public ResponseEntity<AuthResponse> refreshToken (HttpServletRequest request, HttpServletResponse response) {
//        // access token 확인
//        String accessToken = JwtHeaderUtil.getAccessToken(request);
//        AuthToken authToken = authTokenProvider.convertAuthToken(accessToken);
//        if (!authToken.validate()) {
//            return ApiResponse.invalidAccessToken();
//        }
//
//        // expired access token 인지 확인
//        Claims claims = authToken.getExpiredTokenClaims();
//        if (claims == null) {
//            return ApiResponse.notExpiredTokenYet();
//        }
//
//        String userId = claims.getSubject(); // id: social id
//        RoleType roleType = RoleType.of(claims.get("role", String.class));
//
//        // refresh token
//        String refreshToken = ""; // refreshToken body로 받기
//        AuthToken authRefreshToken = authTokenProvider.convertAuthToken(refreshToken);
//
//        if (authRefreshToken.validate()) {
//            return ApiResponse.invalidRefreshToken();
//        }
//
//        // userId refresh token 으로 DB 확인
//
//        // expire calculate
//
//        // refresh 토큰 기간이 3일 이하로 남은 경우, refresh 토큰 갱신
//        // refresh 토큰 설정
//
//            // DB에 refresh 토큰 업데이트
//
//        return ApiResponse.success("token", newAccessToken.getToken());
//    }
}