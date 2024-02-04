package com.roomie.roomie.user.dto.request;

import com.example.roomie.domain.user.enums.Gender;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class SignUpRequestDto {
    @NotBlank
    private String uid;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;
    private Date birth;
    @Email
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String nickname;
    @NotBlank
    private String phoneNum;
    private Gender gender;
    @NotBlank
    private String mbti;

}
