package com.gh.facade;

import com.gh.global.util.ParamUtil;
import com.gh.global.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GyeonggiPhysicalFacade {

    @Value("${api.gyeonggi.physical.uri}")
    private String uri;

    @Value("${api.gyeonggi.physical.key}")
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

        // uri
        String baseUrl = String.format(uri, classNm);
        // uri 파라미터 응답
        String url = ParamUtil.getQueryString(baseUrl, createTennisReq(key, type, min, max));

        return webClientUtil.getRequestNoneAccept(url, clazz);
    }

    /**
     * tennis 요청 파라미터
     * @param key
     * @param type
     * @param min
     * @param max
     * @return
     */
    private Map<String, String> createTennisReq(String key, String type, int min, int max) {
        return Map.of(
                "KEY", key,
                "Type", type,
                "pIndex", String.valueOf(min),
                "pSize", String.valueOf(max)
        );
    }
}
