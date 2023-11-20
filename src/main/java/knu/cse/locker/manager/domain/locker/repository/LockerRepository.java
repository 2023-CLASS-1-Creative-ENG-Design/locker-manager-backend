package knu.cse.locker.manager.domain.locker.repository;

import knu.cse.locker.manager.domain.locker.entity.Locker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LockerRepository extends JpaRepository<Locker, Long> {
}
