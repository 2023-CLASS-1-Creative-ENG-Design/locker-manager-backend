package knu.cse.locker.manager.domain.record.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.record.dto.response.RecordsResponseDto;
import knu.cse.locker.manager.domain.record.entity.LockerStatus;
import knu.cse.locker.manager.domain.record.service.RecordService;
import knu.cse.locker.manager.global.security.details.PrincipalDetails;
import knu.cse.locker.manager.global.utils.api.ApiUtil;
import knu.cse.locker.manager.infra.mail.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "사물함 개폐 여부 기록")
@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;
    private final EmailService emailService;

    @PostMapping
    @Operation(summary = "사물함 개폐 여부 작성", description = "status=open 또는 status=close")
    public ApiUtil.ApiSuccessResult<Long> saveLockerStatus (
            Authentication authentication,
            @RequestParam("status") String status) {

        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        Account account = userDetails.getAccount();

        LockerStatus lockerStatus = LockerStatus.of(status);

        Long record_id = recordService.saveLockerStatus(account, lockerStatus);
        emailService.sendMail(account, lockerStatus);

        return ApiUtil.success(record_id);
    }

    @GetMapping("/now")
    @Operation(summary = "사물함 현재 개폐 여부 상태 조회")
    public ApiUtil.ApiSuccessResult<LockerStatus> readCurrentLockerStatus (
            Authentication authentication) {

        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        Account account = userDetails.getAccount();

        LockerStatus lockerStatus = recordService.readCurrentLockerStatus(account);

        return ApiUtil.success(lockerStatus);
    }

    @GetMapping
    @Operation(summary = "사물함 개폐 여부 전체 조회")
    public ApiUtil.ApiSuccessResult<List<RecordsResponseDto>> readAllLockerStatus (
            Authentication authentication) {

        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        Account account = userDetails.getAccount();

        List<RecordsResponseDto> responseDtos = recordService.readAllLockerStatusRecord(account);

        return ApiUtil.success(responseDtos);
    }
}
