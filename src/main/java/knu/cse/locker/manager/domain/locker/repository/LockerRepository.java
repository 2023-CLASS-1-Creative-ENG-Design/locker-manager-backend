package knu.cse.locker.manager.domain.locker.repository;

import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.locker.entity.Locker;
import knu.cse.locker.manager.domain.locker.entity.LockerLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LockerRepository extends JpaRepository<Locker, Long> {
    Optional<Locker> findByLockerLocationAndLockerNumber(LockerLocation lockerLocation, String lockerNumber);
    Optional<Locker> findByAccount(Account account);
    Boolean existsByAccount(Account account);
}
