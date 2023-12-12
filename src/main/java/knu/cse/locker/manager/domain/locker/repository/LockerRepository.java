package knu.cse.locker.manager.domain.locker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.locker.entity.Locker;
import knu.cse.locker.manager.domain.locker.entity.LockerLocation;

/* 
 * LockerRepository.java
 *
 * @note 사물함 정보를 관리하는 Repository
 *
 * @see knu.cse.locker.manager.domain.locker.entity.Locker
 *
 */

public interface LockerRepository extends JpaRepository<Locker, Long> {
    @EntityGraph(attributePaths = "account")
    Optional<Locker> findByLockerLocationAndLockerNumber(LockerLocation lockerLocation, String lockerNumber);
    Optional<Locker> findByAccount(Account account);
    Boolean existsByAccount(Account account);
}
