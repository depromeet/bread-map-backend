package com.depromeet.breadmapbackend.members.controller;

import com.depromeet.breadmapbackend.bakeries.service.BakeriesService;
import com.depromeet.breadmapbackend.bakeries.service.MenusService;
import com.depromeet.breadmapbackend.common.dto.ApiResponse;
import com.depromeet.breadmapbackend.flags.service.FlagsService;
import com.depromeet.breadmapbackend.members.dto.ProfileBakeryListResponse;
import com.depromeet.breadmapbackend.reviews.service.MenuReviewsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MembersController {

    private final BakeriesService bakeriesService;
    private final MenuReviewsService menuReviewsService;
    private final FlagsService flagsService;
    private final MenusService menusService;

    /**
     * 현재 로그인한 회원이 깃발 꽂은 빵집 리스트 조회
     * @return ResponseEntity<profileBakeryListResponse>
     */
    @ApiOperation(value = "현재 로그인한 회원이 깃발 꽂은 빵집 리스트", notes = "현재 로그인한 회원이 깃발 꽂은 빵집 리스트 조회")
    @GetMapping
    public ResponseEntity<List<ProfileBakeryListResponse>> getBakeryList(@ApiParam(value="flags 또는 reviews(현재는 flags만 가능)", required = true) @RequestParam String type) {
        return ApiResponse.success(null);
    }
}
