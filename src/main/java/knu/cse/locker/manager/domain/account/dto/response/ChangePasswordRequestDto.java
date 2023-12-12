package knu.cse.locker.manager.domain.account.dto.response;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
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
public class ChangePasswordRequestDto {
    @NotNull
    private String currentPassword;
    @NotNull
    private String newPassword;
}
