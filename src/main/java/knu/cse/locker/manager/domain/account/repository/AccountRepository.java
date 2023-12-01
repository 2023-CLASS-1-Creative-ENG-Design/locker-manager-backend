package knu.cse.locker.manager.domain.account.repository;


import knu.cse.locker.manager.domain.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findBySchoolNumber(String schoolNumber);
    boolean existsByEmail(String email);
    boolean existsBySchoolNumber(String email);
}