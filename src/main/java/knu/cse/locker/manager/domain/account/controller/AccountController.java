package knu.cse.locker.manager.domain.account.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import knu.cse.locker.manager.domain.account.dto.response.AccountDetailsResponseDto;
import knu.cse.locker.manager.domain.account.dto.response.ChangeEmailRequestDto;
import knu.cse.locker.manager.domain.account.dto.response.ChangePasswordRequestDto;
import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.account.service.AccountService;
import knu.cse.locker.manager.global.security.details.PrincipalDetails;
import knu.cse.locker.manager.global.utils.api.ApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Tag(name = "사용자 계정")
@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    @Operation(summary = "사용자 정보 조회")
    public ApiUtil.ApiSuccessResult<AccountDetailsResponseDto> readAccountDetails(Authentication authentication){
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        Account account = userDetails.getAccount();

        return ApiUtil.success(accountService.getUserInfo(account));
    }

    @PutMapping("/push-alarm")
    @Operation(summary = "푸쉬알람 동의하기", description = "현재 동의 거부 상태일 경우 동의로 변경, 동의 상태일 경우 동의 거부로 변경합니다.")
    public ApiUtil.ApiSuccessResult<Boolean> updateAccountPushAlarm(Authentication authentication) {
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        Account account = userDetails.getAccount();

        return ApiUtil.success(accountService.updatePushAlarm(account));
    }

    @GetMapping("/password")
    @Operation(summary = "현재 비밀번호 일치 여부")
    public ApiUtil.ApiSuccessResult<Long> checkCurrentPw(
            Authentication authentication,
            @RequestParam("pw") String currentPw){
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        Account account = userDetails.getAccount();

        accountService.checkCurrentPassword(account, currentPw);

        return ApiUtil.success(account.getId());
    }

    @PutMapping("/password")
    @Operation(summary = "비밀번호 변경")
    public ApiUtil.ApiSuccessResult<Long> changePassword(
            Authentication authentication,
            @Valid @RequestBody ChangePasswordRequestDto requestDto){
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        Account account = userDetails.getAccount();

        Long accountId = accountService.changePassword(account, requestDto);

        return ApiUtil.success(accountId);
    }

    @PutMapping("/email")
    @Operation(summary = "이메일 변경")
    public ApiUtil.ApiSuccessResult<Long> changeEmail(
            Authentication authentication,
            @Valid @RequestBody ChangeEmailRequestDto requestDto){
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        Account account = userDetails.getAccount();

        Long accountId = accountService.changeEmail(account, requestDto);

        return ApiUtil.success(accountId);
    }
}