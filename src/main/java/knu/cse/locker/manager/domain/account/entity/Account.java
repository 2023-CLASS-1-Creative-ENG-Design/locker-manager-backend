package knu.cse.locker.manager.domain.account.entity;

import knu.cse.locker.manager.domain.record.entity.LockerStatusRecord;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

/**
 * schoolNumber 학번
 * password 비밀번호
 * role 역할
 * email 이메일
 * phoneNumber 휴대폰 전화번호
 * name 이름
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

    @Email @Column(unique = true)
    private String email;
    private String phoneNumber;
    private String name;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LockerStatusRecord> records = new ArrayList<>();

    @Builder
    public Account(Long id, String schoolNumber, String password, Role role, String email, String phoneNumber, String name, List<LockerStatusRecord> records) {
        this.id = id;
        this.schoolNumber = schoolNumber;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.records = records;
    }
}