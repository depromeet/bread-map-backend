package com.depromeet.breadmapbackend;

import com.depromeet.breadmapbackend.auth.service.AuthService;
import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import com.depromeet.breadmapbackend.bakeries.repository.BakeriesRepository;
import com.depromeet.breadmapbackend.flags.domain.Flags;
import com.depromeet.breadmapbackend.flags.dto.CreateFlagsRequest;
import com.depromeet.breadmapbackend.flags.repository.FlagsQuerydslRepository;
import com.depromeet.breadmapbackend.flags.repository.FlagsRepository;
import com.depromeet.breadmapbackend.flags.service.FlagsService;
import com.depromeet.breadmapbackend.members.domain.Members;
import com.depromeet.breadmapbackend.members.repository.MemberRepository;
import org.javaunit.autoparams.AutoSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FlagsServiceTest {

    @InjectMocks
    private FlagsService flagsService;

    @Mock
    private FlagsQuerydslRepository flagsQuerydslRepository;

    @Mock
    private AuthService authService;

    @Mock
    private BakeriesRepository bakeriesRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private FlagsRepository flagsRepository;

    @ParameterizedTest
    @AutoSource
    void 요청으로_온_객체가_null_이라면_ResponseStatusException이_발생한다(String token, Long bakeryId, CreateFlagsRequest createFlagsRequest, Flags flags) {

        if(createFlagsRequest.getFlagType() == null) {
            Exception exception = assertThrows(ResponseStatusException.class, () -> flagsService.registerFlag(token, bakeryId, createFlagsRequest));
            String exceptedMessage = "존재하지 않는 FlagType입니다.";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(exceptedMessage));
            then(flagsRepository).should(never()).save(flags);
        }
    }

    @ParameterizedTest
    @AutoSource
    void 요청으로_온_객체에_값이_있고_신규깃발정보라면_해당정보를_저장한다(String token, Long bakeryId, Members members, Bakeries bakeries, Long memberId, CreateFlagsRequest createFlagsRequest, Flags flags) {

        if(createFlagsRequest.getFlagType() != null) {
            given(authService.getMemberId(token)).willReturn(memberId);
            given(memberRepository.findById(memberId)).willReturn(Optional.ofNullable(members));
            given(bakeriesRepository.findById(bakeryId)).willReturn(Optional.ofNullable(bakeries));

            when(flagsQuerydslRepository.findByBakeryIdMemberId(bakeryId, memberId)).thenReturn(null);

            flagsService.registerFlag(token, bakeryId, createFlagsRequest);

            if(flags == null) {
                then(flagsRepository).should().save(flags);
            }
        }
    }

    @ParameterizedTest
    @AutoSource
    void 요청으로_온_객체에_값이_있고_이미_존재하는_깃발정보라면_FlagType만_업데이트한다(String token, Long bakeryId, Members members, Bakeries bakeries, Long memberId, CreateFlagsRequest createFlagsRequest, Flags flags) {

        if(createFlagsRequest.getFlagType() != null) {
            given(authService.getMemberId(token)).willReturn(memberId);
            given(memberRepository.findById(memberId)).willReturn(Optional.ofNullable(members));
            given(bakeriesRepository.findById(bakeryId)).willReturn(Optional.ofNullable(bakeries));

            when(flagsQuerydslRepository.findByBakeryIdMemberId(bakeryId, memberId)).thenReturn(flags);

            flagsService.registerFlag(token, bakeryId, createFlagsRequest);

            if(flags != null) {
                flags.updateFlagType(createFlagsRequest.getFlagType());
            }
        }
    }
}
