package knu.cse.locker.manager.account.repository;



import knu.cse.locker.manager.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findBySchoolNum(String schoolNum);
    boolean existsByEmail(String email);
    boolean existsBySchoolNum(String email);
}