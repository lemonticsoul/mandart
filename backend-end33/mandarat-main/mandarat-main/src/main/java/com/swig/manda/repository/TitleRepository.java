package com.swig.manda.repository;

import com.swig.manda.model.Title;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, Long> {
    Title findByMember_UserId(String userId);
}
