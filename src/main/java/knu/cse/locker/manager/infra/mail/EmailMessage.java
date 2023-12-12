package knu.cse.locker.manager.infra.mail;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/* 
 * EmailMessage.java
 *
 * @note 이메일 전송에 사용되는 DTO
 *
 * @see knu.cse.locker.manager.infra.mail.EmailService#sendEmail(EmailMessage)
 *
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailMessage {
    private String to;
    private String subject;
    private String message;

    @Builder
    public EmailMessage(String to, String subject, String message) {
        this.to = to;
        this.subject = subject;
        this.message = message;
    }
}