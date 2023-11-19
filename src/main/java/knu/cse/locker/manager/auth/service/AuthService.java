package knu.cse.locker.manager.auth.service;

import knu.cse.locker.manager.account.entity.Account;
import knu.cse.locker.manager.account.entity.Role;
import knu.cse.locker.manager.account.repository.AccountRepository;
import knu.cse.locker.manager.account.service.AccountService;
import knu.cse.locker.manager.auth.dto.TokenDto;
import knu.cse.locker.manager.auth.dto.request.RegisterRequestDto;
import knu.cse.locker.manager.auth.dto.response.LoginResponseDto;
import knu.cse.locker.manager.exception.NotFoundException;
import knu.cse.locker.manager.security.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    private final AccountRepository accountRepository;
    private final AccountService accountService;


    public LoginResponseDto login(String schoolNum, String password){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(schoolNum, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = jwtTokenProvider.generateTokenDto(authentication);

        Account account = accountRepository.findBySchoolNum(schoolNum)
                .orElseThrow(() -> new NotFoundException("계정이 존재하지 않습니다."));

        return new LoginResponseDto(account, tokenDto);
    }

    @Transactional
    public Long registerForStudent(RegisterRequestDto requestDto){
        if (accountService.isAccountByEmail(requestDto.getEmail())){
            throw new IllegalStateException("중복된 이메일 입니다.");
        }

        if(accountService.isAccountBySchoolNum(requestDto.getSchoolNum())){
            throw new IllegalStateException("중복된 학번 입니다.");
        }

        Account account = accountRepository.save(requestDto.toEntity(passwordEncoder, Role.ROLE_STUDENT));
        return account.getId();
    }

    @Transactional
    public Long registerForManager(RegisterRequestDto requestDto){
        if (accountService.isAccountByEmail(requestDto.getEmail())){
            throw new IllegalStateException("중복된 이메일 입니다.");
        }

        if(accountService.isAccountByEmail(requestDto.getEmail())){
            throw new IllegalStateException("중복된 학번 입니다.");
        }

        Account account = accountRepository.save(requestDto.toEntity(passwordEncoder, Role.ROLE_MANAGER));
        return account.getId();
    }
}
