package com.swig.manda.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor

public class MainTopicDto {
    private Long id;

    private List<String> missionList;

    private String userId;

}
