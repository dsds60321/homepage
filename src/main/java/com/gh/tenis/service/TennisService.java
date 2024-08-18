package com.gh.tenis.service;

import com.gh.facade.GyeonggiPhysicalFacade;
import com.gh.facade.SeoulPhysicalFacade;
import com.gh.facade.constant.Gyeonggi;
import com.gh.facade.constant.Seoul;
import com.gh.tenis.dto.GyeonggiTennisResp;
import com.gh.tenis.dto.SeoulTennisRespDto;
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

    public SeoulTennisRespDto getReservationList() {
        return getReservationList(1, 1000);
    }

    // 예약된 테니스 조회
    public SeoulTennisRespDto getReservationList(int min, int max) {
        SeoulTennisRespDto seoulTennisRespDto = null;
//        GyeonggiTennisResp.PublicTennis gyeonggiTennisRespDto = null;

        try {
//            GyeonggiTennisResp gyeonggiTennisResp = gyeonggiPhysicalFacade.getReservationList(min, max, Gyeonggi.COURT, GyeonggiTennisResp.class);

//            if (gyeonggiTennisResp != null) {
//                gyeonggiTennisRespDto = gyeonggiTennisResp.mergePublicTennis();
//            }

            seoulTennisRespDto = seoulPhysicalFacade.getReservationList(min, max, Seoul.COURT, SeoulTennisRespDto.class);
        } catch (Exception e) {
            log.error("TennisService : {}", e.getMessage());
        }

        if (seoulTennisRespDto == null) {
            return null;
        }


        return seoulTennisRespDto;
    }
}
