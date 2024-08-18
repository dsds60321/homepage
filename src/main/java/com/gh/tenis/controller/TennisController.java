package com.gh.tenis.controller;

import com.gh.global.dto.response.ApiResponse;
import com.gh.tenis.dto.SeoulTennisRespDto;
import com.gh.tenis.service.TennisService;
import com.gh.util.CommonUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tennis")
public class TennisController {

    private final TennisService tennisService;

    // 테니스 예약현황 조회
    // TODO: 추후 정렬 조건 올 수 도 있음
    @PostMapping("/reservation")
    public ResponseEntity<ApiResponse> tennis(HttpServletResponse response) {
        SeoulTennisRespDto reservationList = tennisService.getReservationList();


        if (reservationList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.NOT_FOUND("예약 목록이 없습니다."));
        }

        CommonUtil.setCache(response, 60);
        return ResponseEntity.ok(ApiResponse.SUCCESS(reservationList.getRows(true)));
    }
}
