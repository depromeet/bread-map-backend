package com.depromeet.breadmapbackend.auth.controller;

import com.depromeet.breadmapbackend.auth.dto.KakaoAuthRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping(value = "/")
    public ResponseEntity testRequest() {
        System.out.println("### GET ###");
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     *
     * @param kakaoAuthRequest
     * @return
     */
    @PostMapping(value = "/kakao")
    public ResponseEntity kakaoAuthRequest(@RequestBody KakaoAuthRequest kakaoAuthRequest) {
        System.out.println("###### payload.get('accessToken'):: " + kakaoAuthRequest.getAccessToken());
        // 0. JWT configure 만들기
        // 1. 사용자 정보 API 찌르는 service 구성 및 정보 체크 (요청 자체가 error 나면 > exception 받아서 돌려줄 것 => accesstoken invalid: response code 다르게)
        // 2. 사용자 정보가 db에 있으면 저장안하고
        // 3. 사용자 정보가 없으면 db 저장
        return new ResponseEntity(HttpStatus.CREATED); // 201
    }
}