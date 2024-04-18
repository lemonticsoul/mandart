package com.swig.manda.mandalart.model;

import com.swig.manda.common.signup.model.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter

@Entity
public class Detail {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long missionIndex;


    private List<String> goalList;

    private String UserId;

    private List<String> goalText;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;




}

