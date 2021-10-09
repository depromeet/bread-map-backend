package com.depromeet.breadmapbackend.auth.client;

import com.depromeet.breadmapbackend.auth.dto.KakaoUserResponse;
import com.depromeet.breadmapbackend.members.domain.Members;
import com.depromeet.breadmapbackend.members.enumerate.MemberProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Log4j2
@Component
@RequiredArgsConstructor
public class ClientKakao implements ClientProxy {

    private final WebClient webClient;

    @Override
    public Members getUserData(String accessToken) {
        KakaoUserResponse kakaoUserResponse = webClient.get() // WebClient (HTTP GET)
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve() // response 어떻게 추출할지 명시하는데 사용
                //.onStatus(HttpStatus::is4xxClientError, response -> ...) // TODO 에러 핸들링 필요 (WebClient deafult 4xx, 5xx: WebClientResponseException)
                //.onStatus(HttpStatus::is5xxServerError, response -> ...)
                .bodyToMono(KakaoUserResponse.class) // body부분 받아오기
                .block(); // synchronous

        log.info("KakaoUserResponse: " + kakaoUserResponse.getProperties().getNickname());
        log.info("KakaoUserResponse: " + kakaoUserResponse.getKakaoAccount().getGender());
        log.info("KakaoUserResponse: " + kakaoUserResponse.getKakaoAccount().getEmail());

        return Members.builder()
                .socialId(kakaoUserResponse.getId())
                .name(kakaoUserResponse.getProperties().getNickname())
                .email(kakaoUserResponse.getKakaoAccount().getEmail())
                .gender(kakaoUserResponse.getKakaoAccount().getGender())
                .build();
    }
}
