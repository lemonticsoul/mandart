package com.swig.manda.mandalart.service;


import com.swig.manda.common.login.repository.LoginRepository;
import com.swig.manda.mandalart.dto.DetailDto;
import com.swig.manda.mandalart.dto.MainTopicDto;
import com.swig.manda.mandalart.dto.TitleDto;
import com.swig.manda.mandalart.model.Detail;
import com.swig.manda.mandalart.model.MainTopic;
import com.swig.manda.common.signup.model.Member;
import com.swig.manda.mandalart.model.Title;
import com.swig.manda.mandalart.repository.DetailRepository;
import com.swig.manda.mandalart.repository.MainRepository;
import com.swig.manda.common.signup.repository.SignupRepository;
import com.swig.manda.mandalart.repository.TitleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MadalartService {

    private final MainRepository mainRepository;
    private final DetailRepository detailRepository;


    private final LoginRepository loginRepository;

    private final TitleRepository titleRepository;

    @Autowired
    public MadalartService(MainRepository mainRepository, DetailRepository detailRepository, LoginRepository loginRepository, TitleRepository titleRepository) {
        this.mainRepository = mainRepository;
        this.detailRepository = detailRepository;
        this.loginRepository = loginRepository;
        this.titleRepository=titleRepository;
    }

    public MainTopicDto saveMainTopic(MainTopicDto mainTopicDto) {
        Member member = loginRepository.findByUserId(mainTopicDto.getUserId())
                .orElseThrow(() -> new RuntimeException("Member not found with userId: " + mainTopicDto.getUserId()));

        MainTopic mainTopic = new MainTopic();
        mainTopic.setMissionList(mainTopicDto.getMissionList()); // 직접 할당
        mainTopic.setMember(member);

        MainTopic savedMainTopic = mainRepository.save(mainTopic);

        MainTopicDto dto = new MainTopicDto();
        dto.setId(savedMainTopic.getId());
        dto.setMissionList(savedMainTopic.getMissionList()); // 직접 사용
        dto.setUserId(savedMainTopic.getMember().getUserId());

        return dto;
    }


    public TitleDto saveTitle(TitleDto titleDto){

        Member member = loginRepository.findByUserId(titleDto.getUserId())
                .orElseThrow(() -> new RuntimeException("Member not found with userId: " + titleDto.getUserId()));

        Title title = new Title();
        title.setTitle(titleDto.getTitle());
        title.setMember(member);

        Title savedTitle = titleRepository.save(title);

        TitleDto savedDto = new TitleDto();
        savedDto.setId(savedTitle.getId());
        savedDto.setTitle(savedTitle.getTitle());
        savedDto.setUserId(savedTitle.getMember().getUserId());

        return savedDto;

    }

    public DetailDto saveDetails(DetailDto detailDto) {
        Member member = loginRepository.findByUserId(detailDto.getUserId())
                .orElseThrow(() -> new RuntimeException("Member not found with userId: " + detailDto.getUserId()));

        Detail detail = new Detail();
        detail.setMissionIndex(detailDto.getMissionIndex());
        detail.setGoalList(detailDto.getGoalList());
        detail.setGoalText(detailDto.getGoalText());// 직접 할당
        detail.setMember(member);

        Detail savedDetail = detailRepository.save(detail);

        DetailDto dto = new DetailDto();
        dto.setMissionIndex(savedDetail.getMissionIndex());
        dto.setGoalText(savedDetail.getGoalText());
        dto.setGoalList(savedDetail.getGoalList());
        dto.setId(savedDetail.getId());
        dto.setUserId(savedDetail.getMember().getUserId());

        return dto;
    }


    public List<MainTopicDto> getAllMainTopicsByUserId(String userId) {

        List<MainTopic> mainTopics = mainRepository.findByMember_UserId(userId);



        List<MainTopicDto> mainTopicDtos = mainTopics.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        return mainTopicDtos;
    }

    public TitleDto getTitleByUserId(String userId){

        Title title= titleRepository.findByMember_UserId(userId);

        if (title == null) {
            return null;
        }

        TitleDto titleDto=new TitleDto();
        titleDto.setId(title.getId());
        titleDto.setTitle(title.getTitle());
        titleDto.setUserId(title.getMember().getUserId());

        return titleDto;

    }

    public List<DetailDto> getAllDetailsByUserId(String userId) {

        List<Detail> details = detailRepository.findByMember_UserId(userId);


        List<DetailDto> detailDtos = details.stream()
                .map(this::convertDetailEntityToDto)
                .collect(Collectors.toList());

        return detailDtos;
    }

    private MainTopicDto convertEntityToDto(MainTopic mainTopic) {
        MainTopicDto mainTopicDto = new MainTopicDto();
        mainTopicDto.setId(mainTopic.getId());
        mainTopicDto.setMissionList(mainTopic.getMissionList());
        mainTopicDto.setUserId(mainTopic.getMember().getUserId());

        return mainTopicDto;
    }

    private DetailDto convertDetailEntityToDto(Detail detail) {
        DetailDto detailDto = new DetailDto();
        detailDto.setId(detail.getId());
        detailDto.setMissionIndex(detail.getMissionIndex());
        detailDto.setGoalText(detail.getGoalText());
        detailDto.setGoalList(detail.getGoalList());
        detailDto.setUserId(detail.getMember().getUserId());
        return detailDto;


    }



    public MainTopicDto updateMainTopic(Long mainId, MainTopicDto mainTopicDto) {
        MainTopic mainTopic = mainRepository.findById(mainId)
                .orElseThrow(() -> new RuntimeException("MainTopic not found with id: " + mainId));


        mainTopic.setMissionList(mainTopicDto.getMissionList());
        MainTopic updatedMainTopic = mainRepository.save(mainTopic);


        MainTopicDto dto = new MainTopicDto();
        dto.setId(updatedMainTopic.getId());
        dto.setMissionList(updatedMainTopic.getMissionList());
        dto.setUserId(updatedMainTopic.getMember().getUserId());

        return dto;
    }


    public TitleDto updateTitle(Long titleId, TitleDto titleDto) {
        Optional<Title> titleOptional = titleRepository.findById(titleId);
        if (titleOptional.isPresent()) {
            Title title = titleOptional.get();
            title.setTitle(titleDto.getTitle());


            Member member = loginRepository.findByUserId(titleDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("Member not found with userId: " + titleDto.getUserId()));

            title.setMember(member);


            titleRepository.save(title);


            TitleDto savedDto = new TitleDto();
            savedDto.setId(title.getId());
            savedDto.setTitle(title.getTitle());
            savedDto.setUserId(title.getMember().getUserId());

            return savedDto;
        } else {
            return null;
        }
    }


    public DetailDto updateDetail(Long missionIndex, DetailDto detailDto) {
        Detail detail = detailRepository.findByMissionIndex(missionIndex)
                .orElseThrow(() -> new RuntimeException("Detail not found with missionIndex: " + missionIndex));



        detail.setGoalList(detailDto.getGoalList());
        detail.setGoalText(detailDto.getGoalText());

        Detail savedDetail = detailRepository.save(detail);


        DetailDto updatedDto = new DetailDto();
        updatedDto.setMissionIndex(savedDetail.getMissionIndex());
        updatedDto.setId(savedDetail.getId());
        updatedDto.setGoalList(savedDetail.getGoalList());
        updatedDto.setGoalText(savedDetail.getGoalText());
        updatedDto.setUserId(savedDetail.getMember().getUserId());

        return updatedDto;
    }






}

