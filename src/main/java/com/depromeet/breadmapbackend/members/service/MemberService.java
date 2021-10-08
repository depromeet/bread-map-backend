package com.depromeet.breadmapbackend.members.service;
import com.depromeet.breadmapbackend.auth.client.ClientKakao;
import com.depromeet.breadmapbackend.auth.dto.AuthRequest;
import com.depromeet.breadmapbackend.members.domain.Members;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberService {

    private final ClientKakao clientKakao;

    public void login(AuthRequest authRequest) {
        Members member = clientKakao.getUserData(authRequest.getAccessToken());
        //        return accessToken = tokenProvider.createAuthToken();
        log.info("member social id: " + member.getSocialId());
    }
}
