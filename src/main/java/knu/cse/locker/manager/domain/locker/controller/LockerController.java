package knu.cse.locker.manager.domain.locker.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.locker.dto.request.LockerChangeRequestDto;
import knu.cse.locker.manager.domain.locker.dto.request.LockerPasswordChangeRequestDto;
import knu.cse.locker.manager.domain.locker.service.LockerService;
import knu.cse.locker.manager.global.security.details.PrincipalDetails;
import knu.cse.locker.manager.global.utils.api.ApiUtil;
import lombok.RequiredArgsConstructor;

/* 
 * LockerController.java
 *
 * @note 사용자 사물함 관리 API를 제공하는 컨트롤러
 *
 * POST /lockers : 사물함 설정 또는 변경
 * PUT /lockers/password : 사물함 비밀번호 변경
 * PUT /lockers/reports : 사물함 고장 신고
 *
 */

@Tag(name = "사용자 사물함 관리")
@RestController
@RequestMapping("/lockers")
@RequiredArgsConstructor
public class LockerController {
    private final LockerService lockerService;

    @PostMapping
    @Operation(summary = "사물함 설정 또는 변경")
    public ApiUtil.ApiSuccessResult<Long> changeLocker(
            Authentication authentication,
            @RequestBody LockerChangeRequestDto requestDto
    ){
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        Account account = userDetails.getAccount();

        Long lockerId = lockerService.changeLockerFromAccount(account, requestDto);

        return ApiUtil.success(lockerId);
    }

    @PutMapping("/password")
    @Operation(summary = "사물함 비밀번호 변경")
    public ApiUtil.ApiSuccessResult<Long> changeLockerPassword(
            Authentication authentication,
            @RequestBody LockerPasswordChangeRequestDto requestDto
    ){
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        Account account = userDetails.getAccount();

        Long lockerId = lockerService.changeLockerPassword(account, requestDto);

        return ApiUtil.success(lockerId);
    }

    @PutMapping("/reports")
    @Operation(summary = "사물함 고장 신고")
    public ApiUtil.ApiSuccessResult<Long> reportBrokenLocker(
            Authentication authentication
    ){
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        Account account = userDetails.getAccount();

        Long accountId = lockerService.reportBrokenLocker(account);

        return ApiUtil.success(accountId);
    }
}