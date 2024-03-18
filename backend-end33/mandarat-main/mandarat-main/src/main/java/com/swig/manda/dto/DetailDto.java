package com.swig.manda.dto;


import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DetailDto {
    private Long missionIndex;


    private List<String> goalList;

    private String UserId;

    private List<String> goalText;

}
