package knu.cse.locker.manager.domain.account.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.account.entity.Role;
import knu.cse.locker.manager.domain.locker.entity.Locker;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccountDetailsResponseDto {
    private String schoolNumber;
    private String name;
    private String email;
    private String phoneNumber;
    private Role role;

    @JsonProperty("locker")
    private LockerDto lockerDto;

    @Builder
    public AccountDetailsResponseDto(String schoolNumber, String name, String email, String phoneNumber, Role role, LockerDto lockerDto) {
        this.schoolNumber = schoolNumber;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.lockerDto = lockerDto;
    }

    public static AccountDetailsResponseDto of(Account account, Locker locker) {
        LockerDto lockerDto =
                (locker != null) ? LockerDto.builder()
                        .lockerLocation(locker.getLockerLocation())
                        .lockerNumber(locker.getLockerNumber())
                        .lockerPassword(locker.getLockerPassword())
                        .lockerIsBroken(locker.getLockerIsBroken()).build() : null;

        return AccountDetailsResponseDto.builder()
                .schoolNumber(account.getSchoolNumber())
                .name(account.getName())
                .email(account.getEmail())
                .phoneNumber(account.getPhoneNumber())
                .role(account.getRole())
                .lockerDto(lockerDto).build();
    }
}
