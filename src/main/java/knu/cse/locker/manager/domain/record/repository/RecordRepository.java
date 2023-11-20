package knu.cse.locker.manager.domain.record.repository;

import knu.cse.locker.manager.domain.record.entity.LockerStatusRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<LockerStatusRecord, Long> {
}
