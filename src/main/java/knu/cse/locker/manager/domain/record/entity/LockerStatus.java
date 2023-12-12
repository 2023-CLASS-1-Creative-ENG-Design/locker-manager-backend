package knu.cse.locker.manager.domain.record.entity;

import knu.cse.locker.manager.global.exception.NotFoundException;

/* 
 * LockerStatus.java
 *
 * @note 사물함 상태
 *
 * @see knu.cse.locker.manager.domain.record.entity.Record
 *
 */

public enum LockerStatus {
    OPEN, CLOSE, NONE;

    public static LockerStatus of(String status) {
        try {
            return LockerStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("잘못된 요청입니다.");
        }
    }
}
