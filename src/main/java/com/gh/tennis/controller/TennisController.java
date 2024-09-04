package com.gh.tennis.controller;

import com.gh.global.dto.response.ApiResponse;
import com.gh.tennis.dto.BookingReqDto;
import com.gh.tennis.dto.SeoulTennisResDto;
import com.gh.tennis.service.CrawlService;
import com.gh.tennis.service.TennisService;
import com.gh.global.util.CommonUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tennis")
@Slf4j
public class TennisController {

    private final TennisService tennisService;
    private final CrawlService crawlService;

    // 테니스 예약현황 조회
    // TODO: 추후 정렬 조건 올 수 도 있음
    @PostMapping("/reservation")
    public ResponseEntity<ApiResponse> tennis(HttpServletResponse response) {
        SeoulTennisResDto reservationList = tennisService.getReservationList();

        if (reservationList == null) {
            return ApiResponse.NOT_FOUND("예약 목록이 없습니다.");
        }

        CommonUtil.setCache(response, 60);
        return ApiResponse.SUCCESS(reservationList.getRows(true));
    }

    // 예약 현황 조회
    @PostMapping("/booking")
    public ResponseEntity<ApiResponse> booking(HttpServletResponse response, @RequestBody BookingReqDto bookingReqDto) {

        if (bookingReqDto.getUrl().isEmpty()) {
            return ApiResponse.NOT_FOUND(null);
        }

        List<LinkedHashMap<String, Boolean>> bookingList = crawlService.seoulTennis(bookingReqDto.getUrl());

        if (bookingList.isEmpty()) {
            return ApiResponse.NOT_FOUND(null);
        }

        return ApiResponse.SUCCESS(bookingList);
    }
}
