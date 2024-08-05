package com.gh.tenis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gh.facade.SeoulPhysicalFacade;
import com.gh.tenis.dto.TennisRespDto;
import com.gh.util.DateUtil;
import com.gh.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TennisService {

    private final SeoulPhysicalFacade seoulPhysicalFacade;
    private final RedisUtil redisUtil;

    // 예약된 테니스 조회
    public TennisRespDto getReservationList(int min, int max) {
        TennisRespDto tennisList = null;

        try {
            if (redisUtil.hasKey(getRedisKey())) {
                return redisUtil.get(getRedisKey(), TennisRespDto.class);
            } else {
                tennisList = seoulPhysicalFacade.getReservationList(1, 10, "테니스장", TennisRespDto.class);
                String listToJson = new ObjectMapper().writeValueAsString(tennisList);
                redisUtil.set(getRedisKey(), listToJson, true);
            }
        } catch (Exception e) {
            e.getMessage();
        }


        return tennisList;
    }

    private String getRedisKey() {
        return "테니스장_" + DateUtil.getDate();
    }
}
