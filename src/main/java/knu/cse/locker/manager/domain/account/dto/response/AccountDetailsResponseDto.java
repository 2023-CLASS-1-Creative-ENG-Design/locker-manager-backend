package knu.cse.locker.manager.domain.account.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.account.entity.Role;
import knu.cse.locker.manager.domain.locker.entity.Locker;
import knu.cse.locker.manager.domain.locker.entity.LockerLocation;
import lombok.*;

import javax.persistence.metamodel.StaticMetamodel;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccountDetailsResponseDto {
    private String schoolNumber;
    private String name;
    private String email;
    private String phoneNumber;
    private Role role;
    private LockerDto locker;

    @Builder
    public static class LockerDto {
        private LockerLocation lockerLocation;
        private String lockerNumber;
        private String lockerPassword;
        private Boolean lockerIsBroken;
    }

    @Builder
    public AccountDetailsResponseDto(String schoolNumber, String name, String email, String phoneNumber, Role role, LockerDto locker) {
        this.schoolNumber = schoolNumber;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.locker = locker;
    }

    public static AccountDetailsResponseDto of(Account account, Locker locker) {
        LockerDto lockerDto = LockerDto.builder()
                .lockerLocation(locker.getLockerLocation())
                .lockerNumber(locker.getLockerNumber())
                .lockerPassword(locker.getLockerPassword())
                .lockerIsBroken(locker.getLockerIsBroken())
                .build();

        return AccountDetailsResponseDto.builder()
                .schoolNumber(account.getSchoolNumber())
                .name(account.getName())
                .email(account.getEmail())
                .phoneNumber(account.getPhoneNumber())
                .role(account.getRole())
                .locker(lockerDto).build();
    }
}
