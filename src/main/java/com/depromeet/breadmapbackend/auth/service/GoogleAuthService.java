package com.depromeet.breadmapbackend.auth.service;

import com.depromeet.breadmapbackend.auth.client.ClientGoogle;
import com.depromeet.breadmapbackend.auth.dto.AuthRequest;
import com.depromeet.breadmapbackend.auth.dto.AuthResponse;
import com.depromeet.breadmapbackend.auth.jwt.AuthToken;
import com.depromeet.breadmapbackend.auth.jwt.AuthTokenProvider;
import com.depromeet.breadmapbackend.members.domain.Members;
import com.depromeet.breadmapbackend.members.repository.MemberQuerydslRepository;
import com.depromeet.breadmapbackend.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {

    private final ClientGoogle clientGoogle;
    private final MemberQuerydslRepository memberQuerydslRepository;
    private final AuthTokenProvider authTokenProvider;
    private final MemberRepository memberRepository;

    @Transactional
    public AuthResponse login(AuthRequest authRequest) {
        Members googleMember = clientGoogle.getUserData(authRequest.getAccessToken());
        String socialId = googleMember.getSocialId();
        Members member = memberQuerydslRepository.findBySocialId(socialId);

        AuthToken appToken = authTokenProvider.createUserAppToken(socialId);

        if (member == null) {
            memberRepository.save(googleMember);
            return AuthResponse.builder()
                    .appToken(appToken.getToken())
                    .isNewMember(Boolean.TRUE)
                    .build();
        }

        return AuthResponse.builder()
                .appToken(appToken.getToken())
                .isNewMember(Boolean.FALSE)
                .build();
    }
}
