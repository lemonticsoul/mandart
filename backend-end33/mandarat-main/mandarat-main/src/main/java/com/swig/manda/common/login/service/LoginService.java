package com.swig.manda.common.login.service;

import com.swig.manda.common.login.controller.LoginController;
import com.swig.manda.common.login.repository.LoginRepository;
import com.swig.manda.common.signup.model.Member;
import com.swig.manda.common.signup.repository.SignupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public void updatePassword(String userid, String newPassword) {
        Optional<Member> optionalMember = loginRepository.findByUserId(userid);
        optionalMember.ifPresent(member -> {
            String encodedNewPassword = bCryptPasswordEncoder.encode(newPassword);
            member.setPassword(encodedNewPassword);
            loginRepository.save(member);
        });
    }

    public String findUsernameByEmailAndName(String email, String username) {

        Optional<Member> member = loginRepository.findByEmailAndUsername(email,username);
        return member.map(Member::getUserId).orElse(null);
    }
}
