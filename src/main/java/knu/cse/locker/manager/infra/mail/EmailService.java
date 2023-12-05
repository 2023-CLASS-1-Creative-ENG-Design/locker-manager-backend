package knu.cse.locker.manager.infra.mail;

import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.record.entity.LockerStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    public void sendMail(Account account, LockerStatus lockerStatus) {
        if (!account.getIsPushAlarm()) return;
        if (lockerStatus != LockerStatus.OPEN) return;

        String message =
                account.getName() + "님의 사물함이 누군가에 의해 개방 되었습니다. " +
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
}