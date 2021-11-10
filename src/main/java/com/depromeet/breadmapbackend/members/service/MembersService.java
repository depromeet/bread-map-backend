package com.depromeet.breadmapbackend.members.service;

import com.depromeet.breadmapbackend.auth.service.AuthService;
import com.depromeet.breadmapbackend.members.dto.UserInfoResponse;
import com.depromeet.breadmapbackend.members.repository.MemberQuerydslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MembersService {

    private final MemberQuerydslRepository memberQuerydslRepository;
    private final AuthService authService;

    public UserInfoResponse getUserInfo(String token) {
        Long memberId = authService.getMemberId(token);

        return Optional.ofNullable(memberQuerydslRepository.findByMemberId(memberId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저 정보가 존재하지 않습니다."));
    }
}
