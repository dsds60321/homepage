package com.gh.global.util;

import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public class CommonUtil {
    private static final String CACHE_VALUE = "max-age=%s, public";

    /**
     * 브라우저 캐시
     * @param response
     * @param ms
     */
    public static void setCache(HttpServletResponse response, int ms) {
        response.setHeader("Cache-Control", String.format(CACHE_VALUE, String.valueOf(ms)));
    }

    public static String randomUUid(int length) {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, length);
    }

}
