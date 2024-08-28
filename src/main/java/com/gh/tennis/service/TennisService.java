package com.gh.tennis.service;

import com.gh.facade.GyeonggiPhysicalFacade;
import com.gh.facade.SeoulPhysicalFacade;
import com.gh.facade.constant.Seoul;
import com.gh.tennis.dto.SeoulTennisResDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TennisService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final SeoulPhysicalFacade seoulPhysicalFacade;
    private final GyeonggiPhysicalFacade gyeonggiPhysicalFacade;

    public SeoulTennisResDto getReservationList() {
        return getReservationList(1, 1000);
    }

    // 예약된 테니스 조회
    public SeoulTennisResDto getReservationList(int min, int max) {
        SeoulTennisResDto seoulTennisResDto = null;
//        GyeonggiTennisResp.PublicTennis gyeonggiTennisRespDto = null;

        try {
//            GyeonggiTennisResp gyeonggiTennisResp = gyeonggiPhysicalFacade.getReservationList(min, max, Gyeonggi.COURT, GyeonggiTennisResp.class);

//            if (gyeonggiTennisResp != null) {
//                gyeonggiTennisRespDto = gyeonggiTennisResp.mergePublicTennis();
//            }

            seoulTennisResDto = seoulPhysicalFacade.getReservationList(min, max, Seoul.COURT, SeoulTennisResDto.class);
        } catch (Exception e) {
            log.error("TennisService : {}", e.getMessage());
        }

        if (seoulTennisResDto == null) {
            return null;
        }


        return seoulTennisResDto;
    }
}
