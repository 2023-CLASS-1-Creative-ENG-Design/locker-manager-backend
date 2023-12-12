package knu.cse.locker.manager.domain.auth.dto.request;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.account.entity.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/* 
 * ChangeEmailRequestDto.java
 *
 * @note 이메일 변경 API 요청에 사용되는 DTO
 *
 * @see knu.cse.locker.manager.domain.account.controller.AccountController#updateAccountEmail(ChangeEmailRequestDto, Authentication)
 *
 */

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
                .build();
    }

    @Builder
    public RegisterRequestDto(String schoolNumber, String password, String name, String email, String phoneNumber) {
        this.schoolNumber = schoolNumber;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}