package com.depromeet.breadmapbackend;

import com.depromeet.breadmapbackend.auth.service.AuthService;
import com.depromeet.breadmapbackend.bakeries.service.BakeriesService;
import com.depromeet.breadmapbackend.members.domain.Members;
import com.depromeet.breadmapbackend.members.repository.MemberQuerydslRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
public class BakeriesServiceTest {

    @InjectMocks
    private BakeriesService bakeriesService;

    @Mock
    private MemberQuerydslRepository memberQuerydslRepository;

    @Mock
    private AuthService authService;

    private static final String TOKEN = "1234zbdsdfdf";
    private static final Long BAKERY_ID = 1L;
    private static final Long MEMBER_ID = 1L;

    @Test
    @DisplayName("테스트")
    void test() {
        //when
        Members member = memberQuerydslRepository.findByIdAndBakeryId(MEMBER_ID, BAKERY_ID);

        //then
        //assertThat(member.getId(), )
    }
}
