package com.lec.spring.domain;

// 회원가입시 paramerter 받아오는 DTO (Data Transfer Object)

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserJoinDTO {
    private String username;
    private String password;
}
