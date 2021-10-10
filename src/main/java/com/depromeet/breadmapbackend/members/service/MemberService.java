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
import org.springframework.transaction.annotation.Transactional;



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

        AuthToken appToken = authTokenProvider.createUserAppToken(socialId);
        AuthToken refreshToken = authTokenProvider.createUserRefreshToken(socialId);

        if (member == null) {
            memberRepository.save(kakaoMember);
        }
        // TODO member != null이면서 정보 갱신(예: 동의항목 변경)시 update 코드 필요 (/refresh 완료 후 개발 예정)
        return AuthResponse.builder()
                .appToken(appToken.getToken())
                .refreshToken(refreshToken.getToken())
                .build();
    }
}
