package com.gh.global.service;

import com.gh.global.dto.response.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final StringRedisTemplate stringRedisTemplate;
    private final long AUTH_TTL = 5;

//    private static final String senderEmail = "gunhodev1@gmail.com";

    // 랜덤으로 숫자 생성
    private String createNumber() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 8; i++) { // 인증 코드 8자리
            int index = random.nextInt(3); // 0~2까지 랜덤, 랜덤값으로 switch문 실행

            switch (index) {
                case 0 -> key.append((char) (random.nextInt(26) + 97)); // 소문자
                case 1 -> key.append((char) (random.nextInt(26) + 65)); // 대문자
                case 2 -> key.append(random.nextInt(10)); // 숫자
            }
        }
        return key.toString();
    }

    private String getMailTemplate() {
        try {
            ClassPathResource path = new ClassPathResource("static/template/mail.html");
            return new String(Files.readAllBytes(Paths.get(path.getURI())));
        } catch (IOException e) {
            log.error("MailTemplate 가져오는데 오류가 발생했습니다. : {} ", e.getMessage());
            return null;
        }
    }

    // 메일 생성
    private MimeMessage createMail(String mail, String number) {

        String mailTemplate = getMailTemplate();

        if (mailTemplate == null) {
            return null;
        }

        mailTemplate = mailTemplate.replace("{AUTH_NO}", number);
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom("gunhodev1@gmail.com");
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("GUNHO.DEV 이메일 인증");
            String body = mailTemplate;
            message.setText(body, "UTF-8", "html");

            return message;

        } catch (MessagingException e) {
            log.error("메일 발송 중 에러가 발생했습니다. : {} ", e.getMessage());
            return null;
        }
    }

    // 메일 발송
    public ResponseEntity<?> sendSimpleMessage(String sendEmail){
        String number = createNumber(); // 랜덤 인증번호 생성
        stringRedisTemplate.opsForValue().set(sendEmail, number, AUTH_TTL, TimeUnit.MINUTES);

        MimeMessage message = createMail(sendEmail, number); // 메일 생성

        if (message == null) {
            return ApiResponse.SERVER_ERROR();
        }

        javaMailSender.send(message); // 메일 발송

        return ApiResponse.SUCCESS("메일을 발송 했습니다.");
    }

}
