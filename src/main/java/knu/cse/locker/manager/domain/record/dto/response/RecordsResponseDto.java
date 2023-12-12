package knu.cse.locker.manager.domain.record.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import knu.cse.locker.manager.domain.record.entity.LockerStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/* 
 * RecordsResponseDto.java
 *
 * @note 사물함 변경 API 요청에 사용되는 DTO
 *
 * @see knu.cse.locker.manager.domain.record.controller.RecordController#updateRecord(RecordsResponseDto)
 *
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RecordsResponseDto {
    private LockerStatus lockerStatus;
    private String address;
    private LocalDateTime createdAt;

    @Builder
    public RecordsResponseDto(LockerStatus lockerStatus, String address, LocalDateTime createdAt) {
        this.lockerStatus = lockerStatus;
        this.address = address;
        this.createdAt = createdAt;
    }
}
