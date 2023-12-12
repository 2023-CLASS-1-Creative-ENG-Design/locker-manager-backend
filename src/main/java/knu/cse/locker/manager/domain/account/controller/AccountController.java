package knu.cse.locker.manager.domain.account.controller;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import knu.cse.locker.manager.domain.account.dto.response.AccountDetailsResponseDto;
import knu.cse.locker.manager.domain.account.dto.response.ChangeEmailRequestDto;
import knu.cse.locker.manager.domain.account.dto.response.ChangePasswordRequestDto;
import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.account.service.AccountService;
import knu.cse.locker.manager.global.security.details.PrincipalDetails;
import knu.cse.locker.manager.global.utils.api.ApiUtil;
import knu.cse.locker.manager.infra.authcode.AuthcodeService;
import lombok.RequiredArgsConstructor;

/*
 * AccountController.java
 * 
 * @note 사용자 계정 관련 API를 제공하는 컨트롤러
 * 
 * GET /accounts : 사용자 정보 조회
 * PUT /accounts/push-alarm : 푸쉬알람 동의하기
 * GET /accounts/password : 현재 비밀번호 일치 여부
 * PUT /accounts/password : 비밀번호 변경
 * PUT /accounts/email : 이메일 변경
 * 
 */

@Tag(name = "사용자 계정")
@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final AuthcodeService authcodeService;

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
            @RequestParam String code,
            @Valid @RequestBody ChangeEmailRequestDto requestDto){
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        Account account = userDetails.getAccount();

        authcodeService.checkedFromAuthCode(code);

        Long accountId = accountService.changeEmail(account, requestDto);

        authcodeService.delCodeFromRedis(code);

        return ApiUtil.success(accountId);
    }
}