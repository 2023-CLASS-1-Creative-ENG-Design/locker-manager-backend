package knu.cse.locker.manager.account.service;

import knu.cse.locker.manager.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public boolean isAccountByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }


    public boolean isAccountBySchoolNumber(String schoolNumber) {
        return accountRepository.existsBySchoolNumber(schoolNumber);
    }
}
