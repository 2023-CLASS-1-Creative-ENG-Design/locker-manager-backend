package knu.cse.locker.manager.domain.account.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import knu.cse.locker.manager.domain.account.entity.Account;

/* AccountRepository.java
 *
 * @note 사용자 계정 정보를 관리하는 Repository
 *
 * @see knu.cse.locker.manager.domain.account.entity.Account
 *
 */

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findBySchoolNumber(String schoolNumber);
    boolean existsByEmail(String email);
    boolean existsBySchoolNumber(String email);
}