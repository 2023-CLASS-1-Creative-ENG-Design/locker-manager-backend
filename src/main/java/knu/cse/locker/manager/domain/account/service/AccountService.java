package knu.cse.locker.manager.domain.account.service;

import knu.cse.locker.manager.domain.account.dto.response.AccountDetailsResponseDto;
import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.account.repository.AccountRepository;
import knu.cse.locker.manager.domain.locker.entity.Locker;
import knu.cse.locker.manager.domain.locker.repository.LockerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final LockerRepository lockerRepository;

    public boolean isAccountByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    public boolean isAccountBySchoolNumber(String schoolNumber) {
        return accountRepository.existsBySchoolNumber(schoolNumber);
    }

    @Transactional
    public AccountDetailsResponseDto getUserInfo(Account account) {
        Locker locker = lockerRepository.findByAccount(account)
                .orElse(null);

        return AccountDetailsResponseDto.of(account, locker);
    }
}