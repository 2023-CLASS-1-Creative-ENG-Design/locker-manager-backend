package knu.cse.locker.manager.domain.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/* 
 * TokenDto.java
 *
 * @note 토큰 발급 API 응답에 사용되는 DTO
 *
 * @see knu.cse.locker.manager.domain.auth.controller.AuthController#issueToken(LoginRequestDto)
 *
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TokenDto {
    private String grantType;
    private String accessToken;

    @Builder
    public TokenDto(String grantType, String accessToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
    }
}
