package com.swig.manda.common.signup.model;

import com.swig.manda.mandalart.model.Detail;
import com.swig.manda.mandalart.model.MainTopic;
import com.swig.manda.mandalart.model.Title;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String userId;
    private String password;
    private String role;
    private String provider;
    private String providerId;

    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;
    @CreationTimestamp
    private LocalDateTime regTime;
    @CreationTimestamp
    private LocalDateTime loginTime;

    @Builder
    public Member(String username, String password, String role, LocalDateTime regTime,String provider,String providerId, LocalDateTime loginTime,String email) {
        this.username=username;
        this.password = password;
        this.role = role;
        this.regTime = regTime;
        this.loginTime = loginTime;
        this.providerId= providerId;
        this.provider=provider;
        this.email=email;
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MainTopic> mainTopics;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Detail> details;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Title title;

    public void addMainTopic(MainTopic mainTopic) {
        if (mainTopics == null) {
            mainTopics = new ArrayList<>();
        }
        mainTopics.add(mainTopic);
        mainTopic.setMember(this);
    }
}