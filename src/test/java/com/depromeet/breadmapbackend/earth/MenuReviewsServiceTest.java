package com.depromeet.breadmapbackend.earth;

import com.depromeet.breadmapbackend.auth.service.AuthService;
import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import com.depromeet.breadmapbackend.bakeries.domain.Menus;
import com.depromeet.breadmapbackend.bakeries.repository.BakeriesRepository;
import com.depromeet.breadmapbackend.bakeries.repository.BreadCategoriesQuerydslRepository;
import com.depromeet.breadmapbackend.bakeries.repository.MenusQuerydslRepository;
import com.depromeet.breadmapbackend.bakeries.repository.MenusRepository;
import com.depromeet.breadmapbackend.members.domain.Members;
import com.depromeet.breadmapbackend.members.repository.MemberRepository;
import com.depromeet.breadmapbackend.reviews.domain.MenuReviews;
import com.depromeet.breadmapbackend.reviews.repository.MenuReviewQuerydslRepository;
import com.depromeet.breadmapbackend.reviews.repository.MenuReviewRepository;
import com.depromeet.breadmapbackend.reviews.service.MenuReviewsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class MenuReviewsServiceTest {

    @InjectMocks
    private MenuReviewsService menuReviewsService;

    @Mock
    private MenuReviewRepository menuReviewRepository;

    @Mock
    private MenuReviewQuerydslRepository menuReviewQuerydslRepository;

    @Mock
    private AuthService authService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MenusRepository menusRepository;

    @Mock
    private BakeriesRepository bakeriesRepository;

    @Mock
    private MenusQuerydslRepository menusQuerydslRepository;

    @Mock
    private BreadCategoriesQuerydslRepository breadCategoriesQuerydslRepository;

    private static final String TOKEN = "tokentokentoken";
    private static final Long BAKERY_ID = 1L;
    private static final Long MEMBER_ID = 1L;
    private static final Long MENU_REVIEW_ID = 1L;

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("본인이 작성한 리뷰를 삭제합니다.")
    void deleteMenuReview() {
        // given
        Members member = Members.builder()
                .id(MEMBER_ID)
                .build();

        MenuReviews menuReview = new MenuReviews(MENU_REVIEW_ID, new Menus(), member, new Bakeries(), "", 3L, new ArrayList<>());

        given(authService.getMemberId(any(String.class))).willReturn(MEMBER_ID);
        given(memberRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(member));
        given(menuReviewQuerydslRepository.findByMenuReviewIdAndBakeryId(any(Long.class), any(Long.class))).willReturn(menuReview);
        // when
        menuReviewsService.deleteMenuReview(TOKEN, BAKERY_ID, MENU_REVIEW_ID);
        // then
        then(menuReviewRepository).should().delete(refEq(menuReview));
    }

    @Test
    @DisplayName("본인이 작성하지 않은 리뷰를 삭제하려 하였을 때, 예외가 발생합니다.")
    public void deleteMenuReviewIfMemberIsNotAuthor() {
        // given
        String expectedException = "해당 리뷰 작성자가 아닙니다.";

        Members member = Members.builder()
                .id(MEMBER_ID)
                .build();
        Members otherMember = Members.builder()
                .id(0L)
                .build();
        MenuReviews menuReview = new MenuReviews(MENU_REVIEW_ID, new Menus(), otherMember, new Bakeries(), "", 3L, new ArrayList<>());

        given(authService.getMemberId(any(String.class))).willReturn(0L);
        given(memberRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(member));
        given(menuReviewQuerydslRepository.findByMenuReviewIdAndBakeryId(any(Long.class), any(Long.class))).willReturn(menuReview);

        // when
        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> {
            menuReviewsService.deleteMenuReview(TOKEN, BAKERY_ID, MENU_REVIEW_ID);
        });
        // then
        assertEquals(responseStatusException.getReason(), expectedException);
    }

    @Test
    void createMenuReviewList() {
    }

    @Test
    void getMenuReviewList() {
    }
}
