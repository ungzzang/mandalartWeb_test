package com.green1st.mandalartWeb.user;

import com.green1st.mandalartWeb.user.model.MailUtils;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

@Slf4j
@Service
public class MailSendService {
    @Autowired
    private JavaMailSenderImpl mailSender;

    private static final long EXPIRATION_TIME = 10 * 60 * 1000; // 10분 (밀리초 단위)

    //인증키 생성
    private String getKey(int size) {
        return generateAuthCode(size);
    }

    //인증코드 난수 발생
    private String generateAuthCode(int size) {
        SecureRandom random = new SecureRandom();
        StringBuilder buffer = new StringBuilder();
        while(buffer.length() < size) {
            int num = random.nextInt(10);  // 0-9 범위의 난수 생성
            buffer.append(num);
        }
        return buffer.toString();
    }

    //인증메일 보내기
    public String sendAuthMail(String email) {
        //6자리 난수 인증번호 생성
        String authKey = getKey(6);

        //인증메일 보내기
        try {
            MailUtils sendMail = new MailUtils(mailSender);
            sendMail.setSubject("회원가입 이메일 인증");
            sendMail.setText(new StringBuffer()
                    .append("<h1>[이메일 인증]</h1>")
                    .append("<p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>")
                    .append("<a href='http://localhost:8080/api/user/signUpConfirm?email=")
                    .append(email)
                    .append("&authKey=")
                    .append(authKey)
                    .append("&expiry=")
                    .append(System.currentTimeMillis() + EXPIRATION_TIME)// 만료 시간 추가(현재 시간 + 10분)
                    .append("' target='_blank'>이메일 인증 확인 (클릭)</a>")
                    .append("</div>")
                    .append("<div class='footer'>")
                    .append("<p>감사합니다.<br>관리자 드림</p>")
                    .append("</div>")
                    .toString());
            sendMail.setFrom("mandalart502@gmail.com", "관리자");  // 실제 발신 이메일 주소로 변경
            sendMail.setTo(email);
            sendMail.send();
        } catch (MessagingException e) {
            // 로깅 처리 추가 (개선)
            log.error("이메일 전송 실패: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            // 로깅 처리 추가 (개선)
            log.error("이메일 인코딩 처리 실패: " + e.getMessage());
        }

        return authKey;
    }
}
