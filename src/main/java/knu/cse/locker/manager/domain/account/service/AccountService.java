package knu.cse.locker.manager.domain.account.service;

import knu.cse.locker.manager.domain.account.dto.response.AccountDetailsResponseDto;
import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.account.repository.AccountRepository;
import knu.cse.locker.manager.domain.locker.entity.Locker;
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

    public AccountDetailsResponseDto getUserInfo(Account account) {
        Locker locker = account.getLocker();

        return AccountDetailsResponseDto.of(account, locker);
    }
}
