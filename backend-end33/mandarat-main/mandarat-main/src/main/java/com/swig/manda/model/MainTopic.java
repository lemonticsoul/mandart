package com.swig.manda.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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


