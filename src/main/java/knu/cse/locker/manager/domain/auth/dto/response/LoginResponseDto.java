package knu.cse.locker.manager.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.account.entity.Role;
import knu.cse.locker.manager.domain.auth.dto.TokenDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoginResponseDto {
    private String schoolNumber;

    private String name;
    private String email;
    private String phoneNumber;

    private Role role;

    @JsonProperty("token")
    private TokenDto tokenDto;

    public LoginResponseDto(Account account, TokenDto tokenDto) {
        this.schoolNumber = account.getSchoolNumber();

        this.name = account.getName();
        this.email = account.getEmail();
        this.phoneNumber = account.getPhoneNumber();
        this.role = account.getRole();
        this.tokenDto = tokenDto;
    }
}
