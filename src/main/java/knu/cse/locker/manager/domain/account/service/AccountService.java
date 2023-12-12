package knu.cse.locker.manager.domain.account.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import knu.cse.locker.manager.domain.account.dto.response.AccountDetailsResponseDto;
import knu.cse.locker.manager.domain.account.dto.response.ChangeEmailRequestDto;
import knu.cse.locker.manager.domain.account.dto.response.ChangePasswordRequestDto;
import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.account.repository.AccountRepository;
import knu.cse.locker.manager.domain.locker.entity.Locker;
import knu.cse.locker.manager.domain.locker.repository.LockerRepository;
import knu.cse.locker.manager.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

/* AccountService.java
 *
 * @note 사용자 계정 관련 비즈니스 로직을 담당하는 서비스
 *
 * @see knu.cse.locker.manager.domain.account.controller.AccountController
 *
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final LockerRepository lockerRepository;

    public boolean isAccountByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    public boolean isAccountBySchoolNumber(String schoolNumber) {
        return accountRepository.existsBySchoolNumber(schoolNumber);
    }

    public AccountDetailsResponseDto getUserInfo(Account account) {
        Locker locker = lockerRepository.findByAccount(account)
                .orElse(null);

        return AccountDetailsResponseDto.of(account, locker);
    }

    @Transactional
    public Boolean updatePushAlarm(Account account) {
        account.updatePushAlarm(account.getIsPushAlarm() == null || !account.getIsPushAlarm());

        accountRepository.save(account);
        return account.getIsPushAlarm();
    }

    @Transactional
    public void firstLoginAction(String schoolNum) {
        Account account = accountRepository.findBySchoolNumber(schoolNum)
                .orElseThrow(() -> new NotFoundException("최초 로그인 업데이트 시도 중 오류 발생"));

        if (account.getIsPushAlarm() == null) {
            account.updatePushAlarm(false);
            accountRepository.save(account);
        }
    }

    public void checkCurrentPassword(Account account, String currentPw) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(account.getSchoolNumber(), currentPw);

        authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }

    @Transactional
    public Long changePassword(Account account, ChangePasswordRequestDto requestDto) {
        String currentPw = requestDto.getCurrentPassword();
        String newPw = requestDto.getNewPassword();

        checkCurrentPassword(account, currentPw);
        if (currentPw.equals(newPw)) throw new NotFoundException("똑같은 비밀번호로 변경할 수 없습니다.");

        account.changePassword(passwordEncoder.encode(newPw));

        return accountRepository.save(account).getId();
    }

    @Transactional
    public Long changeEmail(Account account, ChangeEmailRequestDto requestDto) {
        String newEmail = requestDto.getNewEmail();

        if (account.getEmail().equals(newEmail)) throw new NotFoundException("똑같은 이메일로 변경할 수 없습니다.");

        account.changeEmail(newEmail);

        return accountRepository.save(account).getId();
    }
}
