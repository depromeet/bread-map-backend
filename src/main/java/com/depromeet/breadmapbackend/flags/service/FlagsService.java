package com.depromeet.breadmapbackend.flags.service;

import com.depromeet.breadmapbackend.auth.service.AuthService;
import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import com.depromeet.breadmapbackend.bakeries.repository.BakeriesRepository;
import com.depromeet.breadmapbackend.flags.domain.Flags;
import com.depromeet.breadmapbackend.flags.dto.CreateFlagsRequest;
import com.depromeet.breadmapbackend.flags.repository.FlagsQuerydslRepository;
import com.depromeet.breadmapbackend.flags.repository.FlagsRepository;
import com.depromeet.breadmapbackend.members.domain.Members;
import com.depromeet.breadmapbackend.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlagsService {

    private final FlagsQuerydslRepository flagsQuerydslRepository;
    private final AuthService authService;
    private final BakeriesRepository bakeriesRepository;
    private final MemberRepository memberRepository;
    private final FlagsRepository flagsRepository;

    @Transactional
    public void registerFlag(String token, Long bakeryId, CreateFlagsRequest createFlagsRequest) {
        if(createFlagsRequest.getFlagType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 FlagType입니다.");
        }

        Long memberId = authService.getMemberId(token);
        Optional<Bakeries> bakery = bakeriesRepository.findById(bakeryId);
        Optional<Members> member = memberRepository.findById(memberId);
        Flags flag = flagsQuerydslRepository.findByBakeryIdMemberId(bakeryId, memberId);

        if (flag == null) {
            flagsRepository.save(Flags.builder()
                    .members(member.orElseThrow(NullPointerException::new))
                    .bakeries(bakery.orElseThrow(NullPointerException::new))
                    .flagType(createFlagsRequest.getFlagType())
                    .build());
        } else {
            flag.updateFlagType(createFlagsRequest.getFlagType());
        }
    }
}
