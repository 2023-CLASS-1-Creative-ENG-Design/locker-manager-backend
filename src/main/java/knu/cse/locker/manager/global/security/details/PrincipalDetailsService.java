package knu.cse.locker.manager.global.security.details;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

/* 
 * PrincipalDetailsService.java
 *
 * @note spring security에서 사용자 정보를 관리하는 클래스
 *
 * @see knu.cse.locker.manager.domain.account.entity.Account
 *
 */

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account =  accountRepository.findBySchoolNumber(username)
                .orElseThrow(() -> new UsernameNotFoundException("계정을 찾을 수 없습니다."));

        return new PrincipalDetails(account);
    }
}
