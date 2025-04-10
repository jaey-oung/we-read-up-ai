package com.wru.wrubookstore.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"email", "password", "name"})
public class EmployeeDto {
    private String employeeId;      // 직원 코드
    private String authorityId;     // 직원 권한 코드
    private String deptId;          // 직원 부서
    private String email;           // 직원 이메일
    private String password;        // 직원 비밀번호
    private String name;            // 직원 명
}
