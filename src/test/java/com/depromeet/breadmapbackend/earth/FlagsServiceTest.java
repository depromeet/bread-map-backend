package com.depromeet.breadmapbackend.earth;

import com.depromeet.breadmapbackend.auth.service.AuthService;
import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import com.depromeet.breadmapbackend.bakeries.repository.BakeriesRepository;
import com.depromeet.breadmapbackend.common.enumerate.FlagType;
import com.depromeet.breadmapbackend.flags.domain.Flags;
import com.depromeet.breadmapbackend.flags.dto.CreateFlagsRequest;
import com.depromeet.breadmapbackend.flags.repository.FlagsQuerydslRepository;
import com.depromeet.breadmapbackend.flags.repository.FlagsRepository;
import com.depromeet.breadmapbackend.flags.service.FlagsService;
import com.depromeet.breadmapbackend.members.domain.Members;
import com.depromeet.breadmapbackend.members.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class FlagsServiceTest {

    @InjectMocks
    private FlagsService flagsService;

    @Mock
    private AuthService authService;

    @Mock
    private BakeriesRepository bakeriesRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private FlagsQuerydslRepository flagsQuerydslRepository;

    @Mock
    private FlagsRepository flagsRepository;

    private static final String TOKEN = "tokentokentoken";
    private static final Long BAKERY_ID = 1L;
    private static final Long MEMBER_ID = 1L;
    private static final FlagType FLAG_TYPE = FlagType.GONE;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        flagsService = new FlagsService(flagsQuerydslRepository, authService, bakeriesRepository, memberRepository, flagsRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("깃발이 없을 경우, 신규 깃발을 생성한다.")
    public void createFlag() {
        // given
        CreateFlagsRequest createFlagsRequest = new CreateFlagsRequest(FLAG_TYPE);
        Members member = Members.builder()
                .id(MEMBER_ID)
                .flagsList(new ArrayList<>())
                .build();
        Bakeries bakery = Bakeries.builder()
                .id(BAKERY_ID)
                .flagsList(new ArrayList<>())
                .build();

        Flags flag = Flags.builder()
                .bakeries(bakery)
                .members(member)
                .flagType(createFlagsRequest.getFlagType())
                .build();

        given(authService.getMemberId(any(String.class))).willReturn(MEMBER_ID);
        given(bakeriesRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(bakery));
        given(memberRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(member));
        given(flagsQuerydslRepository.findByBakeryIdMemberId(BAKERY_ID, MEMBER_ID)).willReturn(null);

        // when
        flagsService.registerFlag(TOKEN, BAKERY_ID, createFlagsRequest);
        // then
        then(flagsRepository).should().save(refEq(flag));

    }

    @Test
    @DisplayName("깃발이 존재할 경우, 깃발 type을 createFlagsRequest에 전달된 type으로 업데이트한다.")
    void updateFlag() {
        // given
        CreateFlagsRequest createFlagsRequest = new CreateFlagsRequest(FLAG_TYPE);
        Members member = Members.builder()
                .id(MEMBER_ID)
                .flagsList(new ArrayList<>())
                .build();
        Bakeries bakery = Bakeries.builder()
                .id(BAKERY_ID)
                .flagsList(new ArrayList<>())
                .build();
        Flags existFlag = Flags.builder()
                .bakeries(bakery)
                .members(member)
                .flagType(FlagType.NONE)
                .build();
        given(authService.getMemberId(any(String.class))).willReturn(MEMBER_ID);
        given(bakeriesRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(bakery));
        given(memberRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(member));
        given(flagsQuerydslRepository.findByBakeryIdMemberId(BAKERY_ID, MEMBER_ID)).willReturn(existFlag);

        // when
        flagsService.registerFlag(TOKEN, BAKERY_ID, createFlagsRequest);
        // then
        assertEquals(existFlag.getFlagType(), FLAG_TYPE);
    }

    @Test
    @DisplayName("깃발 타입 잘못된 경우 예외 호출")
    public void registerFlagIfNotSetFlagType () {
        // given
        CreateFlagsRequest nullCreateFlagsRequest = new CreateFlagsRequest();
        // when
        
        // then
        assertThrows(ResponseStatusException.class, () -> {
            flagsService.registerFlag(TOKEN, BAKERY_ID, nullCreateFlagsRequest);
        });
    }
}