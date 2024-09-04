package com.gh.global.util;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParamUtil {

    public static void main(String[] args) {
        System.out.println(getQueryString(Map.of("KEY", "3213123", "Type", "json", "pIndex", String.valueOf(1), "pSize", String.valueOf(1000))));


    }

    /**
     * queryString 응답
     *
     * @param params
     * @return
     */
    public static String getQueryString(Map<String, String> params) {
        return getQueryString("", params);
    }

    public static String getQueryString(String baseUrl, Map<String, String> params) {
        StringBuilder url;
        // url 기본 설정
        if (StringUtils.hasText(baseUrl)) {
            baseUrl = baseUrl.endsWith("?") ? baseUrl : baseUrl + "?";
            url = new StringBuilder(baseUrl);
        } else {
            url = new StringBuilder();
        }

        int size = params.size();
        int index = 0;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            url.append(entry.getKey()).append("=").append(entry.getValue());
            if (index++ < size - 1) {
                url.append("&");
            }
        }

        return url.toString();
    }


    // Request TO QueryString
    public static <T> String objectToQueryString(T object) {
        StringBuilder queryString = new StringBuilder("?");
        Field[] fields = object.getClass().getDeclaredFields();

        List<String> queryParams = new ArrayList<>();

        for (Field field : fields) {
            field.setAccessible(true); // private 필드 접근 허용
            try {
                Object value = field.get(object);
                // 필드 값이 null이 아니고, String 또는 기본형 타입일 때만 처리
                if (value != null && (field.getType().isPrimitive() || value instanceof String)) {
                    String encodedKey = URLEncoder.encode(field.getName(), StandardCharsets.UTF_8);
                    String encodedValue = URLEncoder.encode(value.toString(), StandardCharsets.UTF_8);
                    queryParams.add(encodedKey + "=" + encodedValue);
                }
            } catch (Exception e) {
                e.printStackTrace(); // 예외 처리
            }
        }

        queryString.append(String.join("&", queryParams));
        return queryString.toString();

    }

    // Request TO QueryString
    public static <T> String objectToQueryString(T object, Map<String, String> additionalParams) {
        StringBuilder queryString = new StringBuilder(objectToQueryString(object)).append("&");
        List<String> queryParams = new ArrayList<>();

        // 객체의 필드에서 쿼리 파라미터 생성
        if (additionalParams != null) {
            for (Map.Entry<String, String> entry : additionalParams.entrySet()) {
                if (entry.getValue() != null) {
                    String encodedKey = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8);
                    String encodedValue = URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8);
                    queryParams.add(encodedKey + "=" + encodedValue);
                }
            }
        }

        queryString.append(String.join("&", queryParams));
        return queryString.toString();
    }
}
