package com.depromeet.breadmapbackend.auth.service;

import com.depromeet.breadmapbackend.auth.dto.AuthResponse;
import com.depromeet.breadmapbackend.auth.jwt.AuthToken;
import com.depromeet.breadmapbackend.auth.jwt.AuthTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthTokenProvider authTokenProvider;

    public AuthResponse updateToken(AuthToken authToken) {
        Claims claims = authToken.getTokenClaims();
        if (claims == null) {
            return null;
        }

        Long socialId = Long.parseLong(claims.getSubject());

        AuthToken newAppToken = authTokenProvider.createUserAppToken(socialId);

        return AuthResponse.builder()
                .appToken(newAppToken.getToken())
                .build();
    }
}
