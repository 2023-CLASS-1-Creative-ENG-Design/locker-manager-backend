package knu.cse.locker.manager.domain.locker.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import knu.cse.locker.manager.domain.account.entity.Account;
import knu.cse.locker.manager.domain.record.entity.LockerStatusRecord;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/* 
 * Locker.java
 *
 * @note 사물함 정보
 *
 * @see knu.cse.locker.manager.domain.locker.controller.LockerController
 *
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
