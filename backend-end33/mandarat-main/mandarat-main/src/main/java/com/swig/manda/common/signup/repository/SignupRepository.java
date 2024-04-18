package com.swig.manda.common.signup.repository;

import com.swig.manda.common.signup.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;


@Transactional
public interface SignupRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByUserId(String userId);

    Boolean existsByUserId(String userId);



}

