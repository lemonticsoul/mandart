package com.swig.manda.mandalart.repository;

import com.swig.manda.mandalart.model.Title;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, Long> {
    Title findByMember_UserId(String userId);
}
