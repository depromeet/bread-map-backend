package com.depromeet.breadmapbackend.members.service;

import com.depromeet.breadmapbackend.auth.client.ClientKakao;
import com.depromeet.breadmapbackend.auth.dto.AuthRequest;
import com.depromeet.breadmapbackend.auth.dto.AuthResponse;
import com.depromeet.breadmapbackend.auth.jwt.AuthToken;
import com.depromeet.breadmapbackend.auth.jwt.AuthTokenProvider;
import com.depromeet.breadmapbackend.members.domain.Members;
import com.depromeet.breadmapbackend.members.repository.MemberQuerydslRepository;
import com.depromeet.breadmapbackend.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberService {

    private final ClientKakao clientKakao;
    private final MemberQuerydslRepository memberQuerydslRepository;
    private final AuthTokenProvider authTokenProvider;
    private final MemberRepository memberRepository;

    @Transactional
    public AuthResponse login(AuthRequest authRequest) {
        Members kakaoMember = clientKakao.getUserData(authRequest.getAccessToken());
        Long socialId = kakaoMember.getSocialId();
        Members member = memberQuerydslRepository.findBySocialId(socialId);

        AuthToken appToken = authTokenProvider.createAppToken(socialId);
        AuthToken refreshToken = authTokenProvider.createRefreshToken(socialId);

        if (member == null) {
            memberRepository.save(kakaoMember);
        }

        return AuthResponse.builder()
                .appToken(appToken.getToken())
                .refreshToken(refreshToken.getToken())
                .build();
    }
}
