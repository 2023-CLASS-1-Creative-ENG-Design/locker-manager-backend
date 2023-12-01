package knu.cse.locker.manager.domain.auth.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.account.entity.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegisterRequestDto {
    private String schoolNumber;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;

    public Account toEntity(PasswordEncoder passwordEncoder, Role role){
        return Account.builder()
                .schoolNumber(this.schoolNumber)
                .password(passwordEncoder.encode(this.password))
                .role(role)
                .name(this.name)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .isPushAlarm(null)
                .build();
    }
}