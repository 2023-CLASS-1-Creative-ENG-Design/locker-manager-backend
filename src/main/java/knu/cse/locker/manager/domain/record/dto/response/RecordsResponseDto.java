package knu.cse.locker.manager.domain.record.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import knu.cse.locker.manager.domain.record.entity.LockerStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RecordsResponseDto {
    private LockerStatus lockerStatus;
    private LocalDateTime createdAt;

    @Builder
    public RecordsResponseDto(LockerStatus lockerStatus, LocalDateTime createdAt) {
        this.lockerStatus = lockerStatus;
        this.createdAt = createdAt;
    }
}
