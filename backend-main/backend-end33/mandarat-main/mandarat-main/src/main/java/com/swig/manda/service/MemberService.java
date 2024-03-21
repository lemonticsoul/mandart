package com.swig.manda.service;

import com.swig.manda.dto.MemberDto;
import com.swig.manda.model.Member;
import com.swig.manda.repository.MemberRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private MemberRepository memberRepository;

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
        memberRepository.save(member);

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

        return memberRepository.save(user);
    }




    public boolean userEmailCheck(String email, String userid) {
        Optional<Member> optionalMember = memberRepository.findByUserId(userid);
        return optionalMember.map(member -> member.getEmail().equals(email) && member.getUserId().equals(userid)).orElse(false);
    }

    public void updatePassword(String userid, String newPassword) {
        Optional<Member> optionalMember = memberRepository.findByUserId(userid);
        optionalMember.ifPresent(member -> {
            String encodedNewPassword = bCryptPasswordEncoder.encode(newPassword);
            member.setPassword(encodedNewPassword);
            memberRepository.save(member);
        });
    }

    public String findUsernameByEmailAndName(String email, String username) {

        Optional<Member> member = memberRepository.findByEmailAndUsername(email,username);
        return member.map(Member::getUserId).orElse(null);
    }


    private static final Logger log = LoggerFactory.getLogger(MemberService.class);


    public boolean existsByUserid(String userId) {
        boolean exists = memberRepository.existsByUserId(userId);;
        return exists;
    }




}
