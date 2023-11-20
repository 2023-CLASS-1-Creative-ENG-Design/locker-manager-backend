package knu.cse.locker.manager.domain.record.entity;

public enum LockerStatus {
    OPEN, CLOSE, NONE;

    public static LockerStatus of(String status) {
        try {
            return LockerStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return LockerStatus.NONE;
        }
    }
}
