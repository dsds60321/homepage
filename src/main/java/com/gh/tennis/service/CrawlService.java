package com.gh.tennis.service;

import com.gh.global.util.DateUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class CrawlService {

    private static final int WAIT_SECOND = 5;
    private static final String WRAPPER_CALENDER_FORMAT = "calendar_%s";


    // 서울 테니스 크롤링
    public List<LinkedHashMap<String,Boolean>> seoulTennis(String url) {
        List<String> days = DateUtil.getNowDays();
        List<LinkedHashMap<String, Boolean>> bookingDates = new ArrayList<>();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--headless"); // GUI 없이 실행
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(url); // 크롤링할 URL을 입력하세요.
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECOND));

            // 팝업 닫기 클릭
            WebElement popElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("pop_x")));
            popElement.click(); // 클릭 동작

            for (String day : days) {
                String wrapperId = String.format(WRAPPER_CALENDER_FORMAT, day);
                LinkedHashMap<String, Boolean> bookingDate = new LinkedHashMap<>();

                WebElement calendarWrapperElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(wrapperId)));
                if (calendarWrapperElement.getAttribute("class").contains("able")) {
                    bookingDate.put(day, true);
                } else {
                    bookingDate.put(day, false);
                }

                bookingDates.add(bookingDate);
            }

        } catch (TimeoutException e) {
            System.out.println("타임아웃: 요소를 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit(); // 브라우저 닫기
        }

        return bookingDates;
    }
}


