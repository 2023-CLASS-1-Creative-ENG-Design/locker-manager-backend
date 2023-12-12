package knu.cse.locker.manager.domain.locker.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/* 
 * LockerChangeRequestDto.java
 *
 * @note 사물함 변경 API 요청에 사용되는 DTO
 *
 * @see knu.cse.locker.manager.domain.locker.controller.LockerController#updateLocker(LockerChangeRequestDto)
 *
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LockerChangeRequestDto {
    private String lockerName;
}
