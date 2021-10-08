package com.depromeet.breadmapbackend.auth.controller;

import com.depromeet.breadmapbackend.auth.dto.AuthRequest;
import com.depromeet.breadmapbackend.auth.jwt.AuthTokenProvider;
import com.depromeet.breadmapbackend.common.dto.ApiResponse;
import com.depromeet.breadmapbackend.members.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${app.auth.tokenSecret}")
    private String secretKey;

    @Value("{app.auth.tokenExpiry}")
    private String expiry;

    @Value("{app.auth.refreshTokenExpiry}")
    private String refreshExpiry;

    //private final AuthTokenProvider tokenProvider;
    private final static String REFRESH_TOKEN = "refresh_token";

    private final MemberService memberService;

    private final AuthTokenProvider authTokenProvider;

    /**
     * 사용자 로그인 기능
     * @param authRequest
     * @return ResponseEntity
     */
    @PostMapping(value = "/kakao") // 회원가입 또는 AppToken expire
    public ApiResponse<HttpStatus> kakaoAuthRequest(@RequestBody AuthRequest authRequest) {
        memberService.login(authRequest); // userinfo get + save + social id -> create token => return token to body
        return ApiResponse.created(); // 201
    }

    /**
     * 개발예정
     */
//    @GetMapping("/refresh")
//    public ApiResponse<AuthResponse> refreshToken (HttpServletRequest request, HttpServletResponse response) {
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
//                // expire calculate
//
//        // refresh 토큰 기간이 3일 이하로 남은 경우, refresh 토큰 갱신
//            // refresh 토큰 설정
//
//            // DB에 refresh 토큰 업데이트
//
//        return ApiResponse.success("token", newAccessToken.getToken());
//    }
}