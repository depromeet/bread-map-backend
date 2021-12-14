package com.depromeet.breadmapbackend.auth.client;

import com.depromeet.breadmapbackend.auth.dto.GoogleUserResponse;
import com.depromeet.breadmapbackend.auth.enumerate.RoleType;
import com.depromeet.breadmapbackend.auth.exception.TokenValidFailedException;
import com.depromeet.breadmapbackend.members.domain.Members;
import com.depromeet.breadmapbackend.members.enumerate.MemberProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClientGoogle implements ClientProxy {

    private final WebClient webClient;

    // TODO ADMIN 유저 생성 시 getAdminUserData 메소드 생성 필요
    @Override
    public Members getUserData(String accessToken) {
        GoogleUserResponse googleUserResponse = webClient.get()
                .uri("https://oauth2.googleapis.com/tokeninfo", builder -> builder.queryParam("id_token", accessToken).build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenValidFailedException("Social Access Token is unauthorized")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new TokenValidFailedException("Internal Server Error")))
                .bodyToMono(GoogleUserResponse.class)
                .block();

        return Members.builder()
                .socialId(googleUserResponse.getSub())
                .name(googleUserResponse.getName())
                .email(googleUserResponse.getEmail())
                .memberProvider(MemberProvider.GOOGLE)
                .roleType(RoleType.USER)
                .profileImagePath(googleUserResponse.getPicture() != null ? googleUserResponse.getPicture() : "")
                .build();
    }
}
