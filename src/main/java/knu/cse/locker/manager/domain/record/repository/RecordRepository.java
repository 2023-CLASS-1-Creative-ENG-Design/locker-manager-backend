package knu.cse.locker.manager.domain.record.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.locker.entity.Locker;
import knu.cse.locker.manager.domain.record.entity.LockerStatusRecord;

/* 
 * RecordRepository.java
 *
 * @note 사물함 상태 기록 정보를 관리하는 Repository
 *
 * @see knu.cse.locker.manager.domain.record.entity.LockerStatusRecord
 *
 */

public interface RecordRepository extends JpaRepository<LockerStatusRecord, Long> {
    LockerStatusRecord findFirstByAccountAndLockerOrderByCreatedAtDesc(Account account, Locker locker);
    List<LockerStatusRecord> findAllByAccountAndLocker(Account account, Locker locker);
}
