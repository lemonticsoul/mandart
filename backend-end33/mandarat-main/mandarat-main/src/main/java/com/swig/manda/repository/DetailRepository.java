package com.swig.manda.repository;


import com.swig.manda.model.Detail;
import com.swig.manda.model.MainTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetailRepository extends JpaRepository<Detail, Long> {


    List<Detail> findByMember_UserId(String userId);
}