package com.swig.manda.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Title {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String Title;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;



}
