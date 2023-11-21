package knu.cse.locker.manager.domain.record.repository;

import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.locker.entity.Locker;
import knu.cse.locker.manager.domain.record.entity.LockerStatusRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<LockerStatusRecord, Long> {
    LockerStatusRecord findFirstByAccountAndLockerOrderByCreatedAtDesc(Account account, Locker locker);
    List<LockerStatusRecord> findAllByAccountAndLocker(Account account, Locker locker);
}
