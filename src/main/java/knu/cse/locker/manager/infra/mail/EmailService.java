package knu.cse.locker.manager.infra.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.record.entity.LockerStatus;
import knu.cse.locker.manager.global.utils.redis.RedisUtil;
import lombok.RequiredArgsConstructor;

/*
 * EmailService.java
 *
 * @note 이메일 전송 서비스
 *
 */

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;

    public void sendMail(Account account, LockerStatus lockerStatus) {
        if (!account.getIsPushAlarm()) return;
        if (lockerStatus != LockerStatus.OPEN) return;

        String message =
                account.getName() + "님의 사물함이 누군가에 의해 개방 되었습니다. \n" +
                "다음 알림을 원하지 않을 경우 경북대학교 사물함 통합 관리 시스템 웹사이트에 접속해 이메일 푸쉬 알람을 해제하세요.";


        EmailMessage emailMessage = EmailMessage.builder()
                .to(account.getEmail())
                .subject("[ KNU LOCKER MANAGER ] 사물함 행동 감지 알림")
                .message(message).build();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo());
            mimeMessageHelper.setSubject(emailMessage.getSubject());
            mimeMessageHelper.setText(emailMessage.getMessage(), false);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendAuthcodeMail(String email, String authcode) {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(email)
                .subject("경북대학교 사물함 통합 관리 시스템 이메일 인증")
                .message("경북대학교 사물함 통합 관리 시스템 이메일 푸쉬 알람 본인인증 메일입니다. \n" +
                        "인증 번호 : " + authcode
                        + "본인 확인에 주의").build();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo());
            mimeMessageHelper.setSubject(emailMessage.getSubject());
            mimeMessageHelper.setText(emailMessage.getMessage(), false);

            javaMailSender.send(mimeMessage);

            redisUtil.set(email, authcode);
            redisUtil.expire(email, 300);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}