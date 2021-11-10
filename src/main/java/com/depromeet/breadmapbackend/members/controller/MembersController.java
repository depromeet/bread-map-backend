package com.depromeet.breadmapbackend.members.controller;

import com.depromeet.breadmapbackend.auth.jwt.JwtHeaderUtil;
import com.depromeet.breadmapbackend.common.dto.ApiResponse;
import com.depromeet.breadmapbackend.members.dto.ProfileBakeryResponse;
import com.depromeet.breadmapbackend.members.dto.UserInfoResponse;
import com.depromeet.breadmapbackend.members.service.MembersService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MembersController {

    private final MembersService membersService;

    /**
     * 현재 로그인한 회원이 깃발 꽂은 빵집 리스트 조회
     * @return ResponseEntity<profileBakeryListResponse>
     */
    @ApiOperation(value = "현재 로그인한 회원이 깃발 꽂은 빵집 리스트", notes = "현재 로그인한 회원이 깃발 꽂은 빵집 리스트 조회")
    @GetMapping
    public ResponseEntity<List<ProfileBakeryResponse>> getProfileBakeryList(HttpServletRequest request,
                                                                            @ApiParam(value="flags 또는 reviews(현재는 flags만 가능)", required = true) @RequestParam String type) {
        if (!type.equals("flags")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 type입니다.");
        }

        String token = JwtHeaderUtil.getAccessToken(request);
        return ApiResponse.success(membersService.getProfileBakeryList(token));
    }

    /**
     * 현재 로그인한 유저의 정보 조회
     * @param request
     * @return ResponseEntity<UserInfoResponse>
     */
    @ApiOperation(value = "로그인한 유저의 정보 조회", notes = "로그인한 유저의 정보 조회")
    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> getUserInfo(HttpServletRequest request) {
        String token = JwtHeaderUtil.getAccessToken(request);
        return ApiResponse.success(membersService.getUserInfo(token));
    }
}
