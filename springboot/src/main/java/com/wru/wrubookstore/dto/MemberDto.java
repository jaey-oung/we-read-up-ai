package com.wru.wrubookstore.dto;

import com.wru.wrubookstore.domain.FindIdCheck;
import com.wru.wrubookstore.domain.FindPwCheck;
import com.wru.wrubookstore.domain.UserRegisterCheck;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"email", "password", "name", "nickname", "phoneNum", "birthdate", "gender", "image"})
public class MemberDto {
    private Integer memberId;       // 회원 코드
    private Integer userId;         // 사용자 코드
    private String userStatusId;    // 사용자 상태 코드

    @NotBlank(message = "이메일을 입력해주세요.", groups = {UserRegisterCheck.class, FindPwCheck.class})
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;           // 사용자 이메일

    @NotBlank(message = "비밀번호를 입력해주세요.", groups = UserRegisterCheck.class)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,20}$",
            message = "영문 대소문자, 숫자, 특수문자 모두 포함하고 8~20자여야 합니다.", groups = UserRegisterCheck.class)
    private String password;        // 사용자 비밀번호

    @NotBlank(message = "이름을 입력해주세요.", groups = {UserRegisterCheck.class, FindIdCheck.class, FindPwCheck.class})
    private String name;            // 사용자 명
    // boolean 타입 사용 시 Lombok이 isMember() 메서드를 자동 생성
    private Boolean isMember;       // 사용자 회원 여부
    private String membershipId;    // 회원 멤버십 코드

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;        // 회원 닉네임

    @NotBlank(message = "전화번호를 입력해주세요.", groups = {FindIdCheck.class, FindPwCheck.class})
    @Pattern(regexp = "^01[016789]-\\d{3,4}-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다.")
    private String phoneNum;        // 회원 전화번호

    @NotNull(message = "생일을 선택해주세요.")
    @Past(message = "생일은 과거 날짜만 가능합니다.")
    private LocalDate birthdate;   // 회원 생년월일
    private LocalDate regDate;      // 회원 등록 날짜
    private LocalDate modDate;     // 회원 수정 날짜

    @NotNull(message = "성별을 선택해주세요.")
    private char gender;          // 회원 성별
    private Integer mileage;            // 회원 보유 마일리지
    private int lastMonthAmount;    // 회원 전월 구매 금액
    private String image;           // 회원 이미지

    public MemberDto() {}
    public MemberDto(String email, String password, String name, String nickname, String phoneNum, LocalDate birthdate, char gender, String image) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNum = phoneNum;
        this.birthdate = birthdate;
        this.gender = gender;
        this.image = image;
    }

}
