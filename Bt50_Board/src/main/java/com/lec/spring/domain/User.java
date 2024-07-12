package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class    User {
    private Long id;
    private String username;

    @JsonIgnore
    private String password;

    @ToString.Exclude // toString() 에서 제외
    @JsonIgnore
    private String re_password;

    private String name;
    private String email;

    @JsonIgnore
    private LocalDateTime regDate;

    //OAuth2 client
    private String provider;
    private String providerId;
}
