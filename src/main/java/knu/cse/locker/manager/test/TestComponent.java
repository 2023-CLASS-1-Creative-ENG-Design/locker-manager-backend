package knu.cse.locker.manager.test;

import knu.cse.locker.manager.domain.auth.dto.request.RegisterRequestDto;
import knu.cse.locker.manager.domain.auth.service.AuthService;
import knu.cse.locker.manager.domain.locker.entity.Locker;
import knu.cse.locker.manager.domain.locker.entity.LockerLocation;
import knu.cse.locker.manager.domain.locker.repository.LockerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Component
public class TestComponent {
    private final AuthService authService;
    private final LockerRepository lockerRepository;

    @Autowired
    public TestComponent(AuthService authService, LockerRepository lockerRepository) {
        this.authService = authService;
        this.lockerRepository = lockerRepository;
        setTestDataInLocker();
        issueManagerAccount();
    }

    public void setTestDataInLocker() {
        lockerRepository.deleteAll();
        lockerRepository.flush();

        List<Locker> lockers = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> Locker.builder()
                        .lockerLocation(LockerLocation.LOC_L)
                        .lockerPassword("0000")
                        .lockerIsBroken(false)
                        .lockerNumber(String.valueOf(i))
                        .build())
                .toList();

        lockerRepository.saveAll(lockers);

        log.info("테스트 데이터 INSERT 성공");
    }

    public void issueManagerAccount() {
        authService.registerForManager(RegisterRequestDto.builder()
                .schoolNumber("manager")
                .password("31415926435")
                .name("관리자")
                .email("sjsb4838@gmail.com")
                .phoneNumber("010-3388-8264")
                .build());
    }
}
