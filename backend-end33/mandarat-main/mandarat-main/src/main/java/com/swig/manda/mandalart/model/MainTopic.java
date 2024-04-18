package com.swig.manda.mandalart.model;

import com.swig.manda.common.signup.model.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity

@Setter
@Getter
@NoArgsConstructor

public class MainTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private List<String> missionList;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;



}


