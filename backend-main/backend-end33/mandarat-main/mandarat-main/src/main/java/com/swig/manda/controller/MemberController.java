package com.swig.manda.controller;

import com.swig.manda.dto.MemberDto;
import com.swig.manda.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;




@Slf4j

@RestController
@RequestMapping("/member")
public class MemberController {


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    MemberService memberService;

    @PostMapping("/checkUserId")
    public ResponseEntity<?> checkUserIdExists(@Valid @RequestBody MemberDto MemberDto) {
        String userId = MemberDto.getUserId();
        boolean isDuplicateUserId = memberService.existsByUserid(userId);
        if (isDuplicateUserId) {
            return ResponseEntity.badRequest().body("이미 사용 중인 사용자명입니다.");
        }
        return ResponseEntity.ok().body("사용 가능한 사용자명입니다.");
    }



    //회원가입 해야함! post
    @PostMapping("/join")
    public ResponseEntity<?> joinSave(@Valid @RequestBody MemberDto memberDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){

            if (bindingResult.hasFieldErrors("password")) {

                return ResponseEntity.badRequest().body("비밀번호 형식을 다시확인해주세요");
            }
            if (bindingResult.hasFieldErrors("email")){
                return ResponseEntity.badRequest().body("이메일 형식을 다시 확인해주세요");
            }
        }
        if (!memberDto.getPassword().equals(memberDto.getRepassword())) {

            return ResponseEntity.badRequest().body("패스워드가 맞지 않습니다!");
        }


        memberService.join(memberDto);
        return ResponseEntity.ok().body("회원가입을 축하드립니다.");



    }

}