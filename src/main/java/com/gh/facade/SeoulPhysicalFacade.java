package com.gh.facade;

import com.gh.global.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 서울 시내 공공 체육 시설 예약 정보 조회
 */

@Service
@RequiredArgsConstructor
public class SeoulPhysicalFacade {

    @Value("${api.seoul.physical.uri}")
    private String uri;

    @Value("${api.seoul.physical.key}")
    private String key;

    private final WebClientUtil webClientUtil;

    /**
     *
     * 기본 json
     * @param min 페이징 최소
     * @param max 페이징 끝
     * @param classNm 클래스명 ( 테니스, 풋살 )
     */
    public <T> T  getReservationList(String classNm, Class<T> clazz) {
        return getReservationList("json", 1, 1000, classNm, clazz);
    }

    public <T> T  getReservationList(int min, int max, String classNm, Class<T> clazz) {
        return getReservationList("json", min, max, classNm, clazz);
    }

    public <T> T getReservationList(String type, int min, int max, String classNm, Class<T> clazz) {

        if ( min > max) {
            System.out.println("페이징 시작번호가 끝번호보다 클 수 없습니다.");
            return null;
        }

        if ( max > 1000 ) {
            System.out.println("끝번호가 1000을 넘을 수 없습니다.");
            return null;
        }

        if (classNm.isEmpty()) {
            System.out.println("확인 하실 분류를 입력해주세요.");
            return null;
        }

        uri = String.format(this.uri, key, type, min, max, classNm);

        return webClientUtil.getRequest(uri, clazz);
    }

}
