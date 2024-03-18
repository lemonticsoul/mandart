package com.swig.manda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter

@Entity
public class Detail {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long missionIndex;


    private List<String> goalList;

    private String UserId;

    private List<String> goalText;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;




}

