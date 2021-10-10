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
        KakaoUserResponse kakaoUserResponse = webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                // TODO 에러 핸들링 필요 (WebClient default 4xx, 5xx: WebClientResponseException)
                .bodyToMono(KakaoUserResponse.class)
                .block();

        return Members.builder()
                .socialId(kakaoUserResponse.getId())
                .name(kakaoUserResponse.getProperties().getNickname())
                .email(kakaoUserResponse.getKakaoAccount().getEmail())
                .gender(kakaoUserResponse.getKakaoAccount().getGender())
                .memberProvider(MemberProvider.KAKAO)
                .build();
    }
}
