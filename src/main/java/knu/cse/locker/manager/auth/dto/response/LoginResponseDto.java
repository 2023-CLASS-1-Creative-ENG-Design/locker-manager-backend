package knu.cse.locker.manager.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import knu.cse.locker.manager.account.entity.Account;
import knu.cse.locker.manager.account.entity.Role;
import knu.cse.locker.manager.auth.dto.TokenDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoginResponseDto {
    private String schoolNum;

    private String name;
    private String email;
    private String phoneNum;

    private Role role;

    @JsonProperty("token")
    private TokenDto tokenDto;

    public LoginResponseDto(Account account, TokenDto tokenDto) {
        this.schoolNum = account.getSchoolNum();

        this.name = account.getName();
        this.email = account.getEmail();
        this.phoneNum = account.getPhoneNum();
        this.role = account.getRole();
        this.tokenDto = tokenDto;
    }
}
