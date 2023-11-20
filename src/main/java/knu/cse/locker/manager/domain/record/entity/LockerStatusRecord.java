package knu.cse.locker.manager.domain.record.entity;

import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.locker.entity.Locker;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "history")
public class LockerStatusRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LockerStatus lockerStatus;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Account.class)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Locker.class)
    @JoinColumn(name = "locker_id")
    private Locker locker;

    @Builder
    public LockerStatusRecord(Long id, LockerStatus lockerStatus, Account account, Locker locker) {
        this.id = id;
        this.lockerStatus = lockerStatus;
        this.account = account;
        this.locker = locker;
    }
}
