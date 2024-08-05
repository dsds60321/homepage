package com.gh.tenis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gh.apt.dto.AptRe;
import com.gh.facade.SeoulPhysicalFacade;
import com.gh.global.dto.response.ApiResponse;
import com.gh.tenis.dto.TennisRespDto;
import com.gh.util.DateUtil;
import com.gh.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TennisService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final SeoulPhysicalFacade seoulPhysicalFacade;
    private final RedisUtil redisUtil;
    private static final String CLASS = "테니스장";

    public TennisRespDto getReservationList() {
        return getReservationList(1, 1000);
    }

    // 예약된 테니스 조회
    public TennisRespDto getReservationList(int min, int max) {
        TennisRespDto tennisList = null;

        try {
            if (redisUtil.hasKey(getRedisKey())) {
                tennisList = redisUtil.get(getRedisKey(), TennisRespDto.class);
            } else {
                tennisList = seoulPhysicalFacade.getReservationList(min, max, CLASS, TennisRespDto.class);
                redisUtil.set(getRedisKey(), tennisList, true);
            }
        } catch (Exception e) {
            log.error("TennisService : {}", e.getMessage());
        }

        if (tennisList == null) {
            return null;
        }


        return tennisList;
    }

    private String getRedisKey() {
        return CLASS + "_" + DateUtil.getDate();
    }
}
