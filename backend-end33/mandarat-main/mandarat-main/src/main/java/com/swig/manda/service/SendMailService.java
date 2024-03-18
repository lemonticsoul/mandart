package com.swig.manda.service;

import com.swig.manda.dto.MailDto;
import com.swig.manda.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class SendMailService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "whdygks4@gmail.com";

    public void sendResetPasswordEmail(String email, String userId)  {


        try {
            String tempPassword = generateTemporaryPassword();
            MailDto mailDto = constructResetPasswordEmail(email, userId, tempPassword);
            updatePassword(userId, tempPassword);
            sendEmail(mailDto);
        } catch (Exception e) {

            throw e;
        }
    }



    private MailDto constructResetPasswordEmail(String email, String userId, String tempPassword) {

        String title = userId + "님의 만다라트 임시 비밀번호 안내 이메일 입니다.";
        String message = "안녕하세요. 만다라트 임시 비밀번호 안내 관련 이메일 입니다. [" + userId + "] 님의 임시 비밀번호는 "
                + tempPassword + " 입니다.";

        return new MailDto(email, title, message);
    }

    private void updatePassword(String userId, String tempPassword) {
        String encodedPassword = passwordEncoder.encode(tempPassword);
        memberRepository.updatePasswordByUserId(encodedPassword, userId);
    }

    private String generateTemporaryPassword() {

        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                'U', 'V', 'W', 'X', 'Y', 'Z'
        };
        StringBuilder tempPassword = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int index = (int) (Math.random() * charSet.length);
            tempPassword.append(charSet[index]);
        }
        return tempPassword.toString();
    }

    private void sendEmail(MailDto mailDto) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getEmail());
        message.setFrom(FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        mailSender.send(message);
    }
}

