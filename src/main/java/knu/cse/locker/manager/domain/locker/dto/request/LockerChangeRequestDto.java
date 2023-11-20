package knu.cse.locker.manager.domain.locker.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import knu.cse.locker.manager.domain.locker.entity.LockerLocation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LockerChangeRequestDto {
    private String lockerName;

    public LockerLocation getLockerLocation() {
        String loc_str = this.lockerName.split("-")[0];

        return switch (loc_str) {
            case "B1" -> LockerLocation.LOC_B1;
            case "L" -> LockerLocation.LOC_L;
            case "3F" -> LockerLocation.LOC_3F;
            default -> null;
        };
    }

    public String getLockerNumber() {
        return this.lockerName.split("-")[1];
    }
}
