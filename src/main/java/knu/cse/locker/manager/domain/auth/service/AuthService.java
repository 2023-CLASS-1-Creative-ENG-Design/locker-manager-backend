package knu.cse.locker.manager.domain.auth.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.account.entity.Role;
import knu.cse.locker.manager.domain.account.repository.AccountRepository;
import knu.cse.locker.manager.domain.account.service.AccountService;
import knu.cse.locker.manager.domain.auth.dto.TokenDto;
import knu.cse.locker.manager.domain.auth.dto.request.RegisterRequestDto;
import knu.cse.locker.manager.domain.auth.dto.response.LoginResponseDto;
import knu.cse.locker.manager.global.exception.NotFoundException;
import knu.cse.locker.manager.global.security.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

/* 
 * AuthService.java
 *
 * @note 사용자 인증 관련 비즈니스 로직을 담당하는 서비스
 *
 * @see knu.cse.locker.manager.domain.auth.controller.AuthController
 *
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    private final AccountRepository accountRepository;
    private final AccountService accountService;


    public LoginResponseDto login(String schoolNumber, String password){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(schoolNumber, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = jwtTokenProvider.generateTokenDto(authentication);

        Account account = accountRepository.findBySchoolNumber(schoolNumber)
                .orElseThrow(() -> new NotFoundException("계정이 존재하지 않습니다."));

        return new LoginResponseDto(account, tokenDto);
    }

    @Transactional
    public Long registerForStudent(RegisterRequestDto requestDto){
        if (accountService.isAccountByEmail(requestDto.getEmail())){
            throw new IllegalStateException("중복된 이메일 입니다.");
        }

        if(accountService.isAccountBySchoolNumber(requestDto.getSchoolNumber())){
            throw new IllegalStateException("중복된 학번 입니다.");
        }

        Account account = accountRepository.save(requestDto.toEntity(passwordEncoder, Role.ROLE_STUDENT));
        return account.getId();
    }

    @Transactional
    public void registerForManager(RegisterRequestDto requestDto){
        if (accountService.isAccountByEmail(requestDto.getEmail())){
            throw new IllegalStateException("중복된 이메일 입니다.");
        }

        if(accountService.isAccountByEmail(requestDto.getEmail())){
            throw new IllegalStateException("중복된 학번 입니다.");
        }

        accountRepository.save(requestDto.toEntity(passwordEncoder, Role.ROLE_MANAGER));
    }
}
