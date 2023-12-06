package knu.cse.locker.manager.domain.record.service;

import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.locker.entity.Locker;
import knu.cse.locker.manager.domain.locker.repository.LockerRepository;
import knu.cse.locker.manager.domain.record.dto.response.RecordsResponseDto;
import knu.cse.locker.manager.domain.record.entity.LockerStatus;
import knu.cse.locker.manager.domain.record.entity.LockerStatusRecord;
import knu.cse.locker.manager.domain.record.repository.RecordRepository;
import knu.cse.locker.manager.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordService {
    private final RecordRepository recordRepository;
    private final LockerRepository lockerRepository;

    @Transactional
    public Long saveLockerStatus(
            final Account account,
            final LockerStatus lockerStatus
    ) {
        Locker locker = lockerRepository.findByAccount(account)
                .orElseThrow(() -> new NotFoundException("Locker을 찾을 수 없습니다."));

        LockerStatusRecord record = recordRepository.save(LockerStatusRecord.builder()
                .account(account)
                .locker(locker)
                .lockerStatus(lockerStatus)
                .build());

        return record.getId();
    }

    public LockerStatus readCurrentLockerStatus(final Account account) {
        try {
            Locker locker = lockerRepository.findByAccount(account)
                    .orElseThrow(() -> new NotFoundException("Locker을 찾을 수 없습니다."));

            LockerStatusRecord record = recordRepository.findFirstByAccountAndLockerOrderByCreatedAtDesc(account, locker);

            return record.getLockerStatus();
        } catch (Exception e) {
            log.error("readCurrentLockerStatus() ERROR... " + e);
            throw new NotFoundException("잘못된 접근입니다.");
        }
    }

    public List<RecordsResponseDto> readAllLockerStatusRecord(final Account account) {
        Locker locker = lockerRepository.findByAccount(account)
                .orElseThrow(() -> new NotFoundException("Locker을 찾을 수 없습니다."));

        return recordRepository.findAllByAccountAndLocker(account, locker).stream()
                .map(record -> RecordsResponseDto.builder()
                        .lockerStatus(record.getLockerStatus())
                        .createdAt(record.getCreatedAt())
                        .build())
                .toList();
    }
}
