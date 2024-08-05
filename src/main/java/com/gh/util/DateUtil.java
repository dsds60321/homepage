package com.gh.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * 날짜 유틸
 */
public class DateUtil {

    private static final String YYYYMMDD_REGEX = "^\\d{8}$"; // yyyyMMdd 형식
    private static final String YYYY_MM_DD_REGEX = "^\\d{4}-\\d{2}-\\d{2}$"; // yyyy-MM-dd 형식

    public static String getDate() {
        return getDate("", "yyyyMMdd");
    }

    public static String getDate(String format) {
        return getDate("", format);
    }

    public static String getDate(String dateStr, String format) {
        LocalDate date;
        DateTimeFormatter formatter;
        try {
            formatter = DateTimeFormatter.ofPattern(format);

            if (dateStr.isEmpty()) {
                date = LocalDate.now();
            } else {
                if (Pattern.matches(YYYYMMDD_REGEX, dateStr)) {
                    date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyyMMdd"));
                } else if (Pattern.matches(YYYY_MM_DD_REGEX, format)) {
                    date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } else {
                    throw new RuntimeException("포맷에 맞지 않은 형식입니다");
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return date.format(formatter);
    }


    /**
     * 현재부터 내일 00시까지에 남은 시간
     * @return
     */
    public static long getSecondsUntilMidnight() {
        long seconds;
        // 오늘의 시작 시간
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        LocalDateTime tomorrow = today.plusDays(1).atStartOfDay();

        // 남은 시간 계산 (초 단위)
        seconds = tomorrow.atZone(ZoneId.systemDefault()).toEpochSecond() - now.atZone(ZoneId.systemDefault()).toEpochSecond();

        return seconds;
    }
}
