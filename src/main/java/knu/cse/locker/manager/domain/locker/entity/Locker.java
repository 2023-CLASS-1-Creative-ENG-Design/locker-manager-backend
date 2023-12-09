package knu.cse.locker.manager.domain.locker.entity;

import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.record.entity.LockerStatusRecord;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @note 사물함 정보 및 상태를 관리 하는 ENTITY
 * *
 * lockerLocation 사물함 위치
 * lockerNumber 사물함 번호
 * lockerPassword 사물함 비밀번호
 * lockerIsBroken 사물함 고장 여부
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "locker")
public class Locker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locker_id", unique = true)
    private Long id;

    private LockerLocation lockerLocation;
    private String lockerNumber;

    private String lockerPassword;
    private Boolean lockerIsBroken;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "locker", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<LockerStatusRecord> records = new ArrayList<>();

    public void assignAccount(Account account) {
        this.account = account;
    }

    public void assignLockerPassword(String lockerPassword) {
        this.lockerPassword = lockerPassword;
    }

    public void changeBrokenStatus(Boolean lockerIsBroken) {
        this.lockerIsBroken = lockerIsBroken;
    }

    public void unAssignAccount() {
        this.account = null;
        this.records.clear();
    }

    @Builder
    public Locker(Long id, LockerLocation lockerLocation, String lockerNumber, String lockerPassword, Boolean lockerIsBroken, List<LockerStatusRecord> records) {
        this.id = id;
        this.lockerLocation = lockerLocation;
        this.lockerNumber = lockerNumber;
        this.lockerPassword = lockerPassword;
        this.lockerIsBroken = lockerIsBroken;
        this.records = records;
    }
}
