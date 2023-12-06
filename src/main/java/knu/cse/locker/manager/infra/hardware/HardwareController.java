package knu.cse.locker.manager.infra.hardware;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.locker.service.LockerService;
import knu.cse.locker.manager.domain.record.entity.LockerStatus;
import knu.cse.locker.manager.domain.record.service.RecordService;
import knu.cse.locker.manager.global.exception.NotFoundException;
import knu.cse.locker.manager.global.utils.api.ApiUtil;
import knu.cse.locker.manager.infra.mail.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "하드웨어 제공 API")
@RestController
@RequestMapping("/hardware")
@RequiredArgsConstructor
public class HardwareController {
    private final LockerService lockerService;
    private final RecordService recordService;
    private final EmailService emailService;

    @GetMapping
    @Operation(summary = "사물함 개폐 여부 작성", description = "status=open 또는 status=close, locker=L-60")
    public ApiUtil.ApiSuccessResult<Long> saveLockerStatusForHardware (
            @RequestParam("status") String status,
            @RequestParam("locker") String locker) {

        Account account = lockerService.findByLockerName(locker).getAccount();

        if (account == null) {
            throw new NotFoundException("해당 사물함과 연결된 계정이 존재하지 않습니다.");
        }

        LockerStatus lockerStatus = LockerStatus.of(status);

        Long record_id = recordService.saveLockerStatus(account, lockerStatus);
        emailService.sendMail(account, lockerStatus);

        return ApiUtil.success(record_id);
    }
}
