package com.swig.manda.controller;


import com.swig.manda.config.auth.PrincipalDetails;
import com.swig.manda.dto.DetailDto;
import com.swig.manda.dto.MainTopicDto;
import com.swig.manda.dto.TitleDto;
import com.swig.manda.model.Detail;
import com.swig.manda.service.MadalartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;




@RestController
@RequestMapping("/members/{userId}")
public class MandalartController {

    private final MadalartService madalartService;

    @Autowired
    public MandalartController(MadalartService madalartService) {
        this.madalartService = madalartService;
    }

    @GetMapping("/main")
    public ResponseEntity<Object> getAllMainTopics(@PathVariable String userId) {
        List<MainTopicDto> mainTopics = madalartService.getAllMainTopicsByUserId(userId);
        if (mainTopics == null) {
            mainTopics = new ArrayList<>();
        }
        return ResponseEntity.ok(mainTopics);
    }

    @GetMapping("/title")
    public ResponseEntity<Object> getTitle(@PathVariable String userId) {
        TitleDto title = madalartService.getTitleByUserId(userId);
        return ResponseEntity.ok(title);
    }

    @GetMapping("details")
    public ResponseEntity<Object> getAllDetailsByUserId(@PathVariable String userId) {
        List<DetailDto> details = madalartService.getAllDetailsByUserId(userId);
        if (details == null) {
            details = new ArrayList<>();
        }
        return ResponseEntity.ok(details);
    }


    @PostMapping("/title")
    public ResponseEntity<TitleDto> saveTitle(@PathVariable String userId, @Valid @RequestBody TitleDto titleDto) {
        titleDto.setUserId(userId);
        TitleDto savedTitle = madalartService.saveTitle(titleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTitle);
    }

    @PostMapping("/main")
    public ResponseEntity<MainTopicDto> saveMainTopic(@PathVariable String userId, @Valid @RequestBody MainTopicDto mainTopicDto) {
        mainTopicDto.setUserId(userId);
        MainTopicDto savedTopic = madalartService.saveMainTopic(mainTopicDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTopic);
    }


    @PostMapping("/details")
    public ResponseEntity<DetailDto> saveDetail(@PathVariable String userId, @Valid @RequestBody DetailDto detailDto) {

        detailDto.setUserId(userId);
        DetailDto savedDetail = madalartService.saveDetails(detailDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDetail);

    }

    @PutMapping("/title/{titleId}")
    public ResponseEntity<TitleDto> updateTitle(@PathVariable Long titleId,@RequestBody TitleDto titleDto) {
        TitleDto title= madalartService.updateTitle(titleId,titleDto);
        if (title == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(title);
    }

    @PutMapping("/main/{mainId}")
    public ResponseEntity<MainTopicDto> updateMainTopic(@PathVariable Long mainId, @RequestBody MainTopicDto mainTopicDto) {
        MainTopicDto updatedTopic = madalartService.updateMainTopic(mainId, mainTopicDto);
        if (updatedTopic == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedTopic);
    }


    @PutMapping("/details/{detailId}")
    public ResponseEntity<DetailDto> updateDetail(@PathVariable Long detailId, @Valid @RequestBody DetailDto detailDto) {
        DetailDto updatedDetail = madalartService.updateDetail(detailId, detailDto);
        if (updatedDetail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedDetail);
    }
}

