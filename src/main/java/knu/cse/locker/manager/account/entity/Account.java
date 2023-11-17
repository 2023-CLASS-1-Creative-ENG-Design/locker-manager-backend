package knu.cse.locker.manager.account.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;

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
    private String schoolNum; // 학번
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Email @Column(unique = true)
    private String email;
    private String phoneNum; // 전번
    private String name; // 이름

    @Builder
    public Account(Long id, String schoolNum, String password, Role role, String email, String phoneNum, String name) {
        this.id = id;
        this.schoolNum = schoolNum;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phoneNum = phoneNum;
        this.name = name;
    }
}