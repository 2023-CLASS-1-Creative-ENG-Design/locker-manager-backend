package knu.cse.locker.manager.domain.account.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import knu.cse.locker.manager.domain.record.entity.LockerStatusRecord;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/* 
 * Account.java
 *
 * @note 사용자 계정 정보
 *
 * @see knu.cse.locker.manager.domain.account.controller.AccountController
 *
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", unique = true)
    private Long id;

    @Column(unique = true)
    private String schoolNumber;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String name;
    @Email @Column(unique = true)
    private String email;
    private String phoneNumber;

    private Boolean isPushAlarm;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LockerStatusRecord> records = new ArrayList<>();

    @Builder
    public Account(Long id, String schoolNumber, String password, Role role, String name, String email, String phoneNumber, Boolean isPushAlarm, List<LockerStatusRecord> records) {
        this.id = id;
        this.schoolNumber = schoolNumber;
        this.password = password;
        this.role = role;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isPushAlarm = isPushAlarm;
        this.records = records;
    }

    public void updatePushAlarm(Boolean isPushAlarm) {
        this.isPushAlarm = isPushAlarm;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeEmail(String email) {
        this.email = email;
    }
}