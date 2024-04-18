package com.swig.manda.common.signup.service;

import com.swig.manda.common.signup.dto.MemberDto;
import com.swig.manda.common.signup.model.Member;
import com.swig.manda.common.signup.repository.SignupRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class MemberService {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private SignupRepository signupRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(@Valid MemberDto memberDto){

        String encodedPassword=passwordEncoder.encode(memberDto.getPassword());

        Member member=new Member();
        member.setUserId(memberDto.getUserId());
        member.setPassword(encodedPassword);
        member.setRole(memberDto.getRole());
        member.setEmail(memberDto.getEmail());
        member.setUsername(memberDto.getUsername());
        signupRepository.save(member);

    }

    public Member registerNewOAuth2User(String provider, String providerId, String nickname,String email) {

        String encodedPassword = bCryptPasswordEncoder.encode("temporary-password");

        Member user = Member.builder()
                    .username(nickname)
                    .password(encodedPassword)
                    .role("USER")
                .email(email)
                    .provider(provider)
                    .providerId(providerId)
                    .build();

        return signupRepository.save(user);
    }




    public boolean userEmailCheck(String email, String userid) {
        Optional<Member> optionalMember = signupRepository.findByUserId(userid);
        return optionalMember.map(member -> member.getEmail().equals(email) && member.getUserId().equals(userid)).orElse(false);
    }




    public boolean existsByUserid(String userId) {
        boolean exists = signupRepository.existsByUserId(userId);;
        return exists;
    }




}
