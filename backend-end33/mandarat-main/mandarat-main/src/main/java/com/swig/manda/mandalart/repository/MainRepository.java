package com.swig.manda.mandalart.repository;


import com.swig.manda.mandalart.model.MainTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// MainRepository.java
public interface MainRepository extends JpaRepository<MainTopic, Long> {
    List<MainTopic> findByMember_UserId(String userId);
    Optional<MainTopic> findByIdAndMember_UserId(Long topicId, String userId);

}
