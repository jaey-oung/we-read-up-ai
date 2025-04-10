package com.wru.wrubookstore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"email", "password", "name"})
public class UserDto {
    private Integer userId;         // 사용자 코드
    private String userStatusId;    // 사용자 상태 코드

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;           // 사용자 이메일

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,20}$",
            message = "영문 대소문자/숫자/특수문자 중 2가지 이상 조합, 8자~20자여야 합니다.")
    private String password;        // 사용자 비밀번호

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;            // 사용자 명
    // boolean 타입 사용 시 Lombok이 isMember() 메서드를 자동 생성
    private Boolean isMember;       // 사용자 회원 여부

    public UserDto() {}
    public UserDto(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
