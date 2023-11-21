package knu.cse.locker.manager.domain.locker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import knu.cse.locker.manager.domain.account.dto.response.AccountDetailsResponseDto;
import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.account.service.AccountService;
import knu.cse.locker.manager.domain.locker.dto.request.LockerChangeRequestDto;
import knu.cse.locker.manager.domain.locker.entity.Locker;
import knu.cse.locker.manager.domain.locker.service.LockerService;
import knu.cse.locker.manager.global.security.details.PrincipalDetails;
import knu.cse.locker.manager.global.utils.api.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자 사물함 관리")
@RestController
@RequestMapping("/api/v1/lockers")
@RequiredArgsConstructor
public class LockerController {
    private final LockerService lockerService;

    @PostMapping
    @Operation(summary = "사물함 설정 또는 변경")
    public ApiUtil.ApiSuccessResult<Long> changeLocker(
            Authentication authentication,
            @RequestBody LockerChangeRequestDto lockerChangeRequestDto
    ){
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        Account account = userDetails.getAccount();

        Long account_id = lockerService.changeLockerFromAccount(account, lockerChangeRequestDto);

        return ApiUtil.success(account_id);
    }
}