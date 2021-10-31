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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(FlagsServiceTest.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        flagsService = new FlagsService(flagsQuerydslRepository, authService, bakeriesRepository, memberRepository, flagsRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("깃발 업데이트")
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

        LOGGER.info("깃발 업데이트 전");
        assertNotEquals(existFlag.getFlagType(), FlagType.GONE);
        // when
        if (existFlag == null) {
            flagsRepository.save(Flags.builder()
                    .members(member)
                    .bakeries(bakery)
                    .flagType(createFlagsRequest.getFlagType())
                    .build());
        } else {
            existFlag.updateFlagType(createFlagsRequest.getFlagType());
        }
        // then
        LOGGER.info("깃발 업데이트 후");
        assertEquals(existFlag.getFlagType(), FlagType.GONE);
    }

    @Test
    @DisplayName("깃발 타입 잘못된 경우 예외 호출")
    public void registerFlagIfNotSetFlagType () throws Exception {
        // given
        CreateFlagsRequest nullCreateFlagsRequest = new CreateFlagsRequest();
        // when
        
        // then
        assertThrows(ResponseStatusException.class, () -> {
            flagsService.registerFlag(TOKEN, BAKERY_ID, nullCreateFlagsRequest);
        });
    }
}