package com.swig.manda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PWupdateDto {

    @NotBlank(message = "아이디는 필수입니다.")
    private String userId;
    @NotBlank(message = "현재 비밀번호는 필수 입니다.")
    private String  password;


    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 8~16자 영문, 숫자, 특수문자를 사용하세요.")
    private String newPassword;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 8~16자 영문, 숫자, 특수문자를 사용하세요.")
    private String newRepassword;


}
