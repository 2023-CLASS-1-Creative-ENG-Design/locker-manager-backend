package knu.cse.locker.manager.domain.locker.service;

import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.account.repository.AccountRepository;
import knu.cse.locker.manager.domain.locker.dto.request.LockerChangeRequestDto;
import knu.cse.locker.manager.domain.locker.entity.Locker;
import knu.cse.locker.manager.domain.locker.entity.LockerLocation;
import knu.cse.locker.manager.domain.locker.repository.LockerRepository;
import knu.cse.locker.manager.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LockerService {
    private final AccountRepository accountRepository;
    private final LockerRepository lockerRepository;

    @Transactional
    public Long changeLockerFromAccount(Account account, final LockerChangeRequestDto requestDto) {
        LockerLocation lockerLocation = requestDto.getLockerLocation();
        String lockerNumber = requestDto.getLockerNumber();

        Locker locker = lockerRepository.findByLockerLocationAndLockerNumber(lockerLocation, lockerNumber)
                        .orElseThrow(() -> new NotFoundException("Locker을 찾을 수 없습니다."));

        accountRepository.save(account.assignLocker(locker));

        return account.getId();
    }
}
