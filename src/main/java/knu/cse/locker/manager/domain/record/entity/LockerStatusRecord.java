package knu.cse.locker.manager.domain.record.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.locker.entity.Locker;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/* 
 * LockerStatusRecord.java
 *
 * @note 사물함 변경 기록
 *
 * @see knu.cse.locker.manager.domain.record.controller.RecordController
 *
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

    private String address;

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
    public LockerStatusRecord(Long id, LockerStatus lockerStatus, String address, LocalDateTime createdAt, Account account, Locker locker) {
        this.id = id;
        this.lockerStatus = lockerStatus;
        this.address = address;
        this.createdAt = createdAt;
        this.account = account;
        this.locker = locker;
    }
}
