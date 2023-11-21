package knu.cse.locker.manager.domain.record.entity;

import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.locker.entity.Locker;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @note 사물함 개폐 여부를 저장하는 ENTITY
 * *
 * lockerStatus 사물함 개폐 여부
 * createdAt 현재 시간
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "record")
public class LockerStatusRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id", unique = true)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LockerStatus lockerStatus;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Account.class)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Locker.class)
    @JoinColumn(name = "locker_id")
    private Locker locker;

    @Builder
    public LockerStatusRecord(Long id, LockerStatus lockerStatus, LocalDateTime createdAt, Account account, Locker locker) {
        this.id = id;
        this.lockerStatus = lockerStatus;
        this.createdAt = createdAt;
        this.account = account;
        this.locker = locker;
    }
}
