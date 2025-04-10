package com.wru.wrubookstore.domain;

import jakarta.validation.groups.Default;

public interface AllCheck extends Default, FindIdCheck, FindPwCheck, UserRegisterCheck {
}
