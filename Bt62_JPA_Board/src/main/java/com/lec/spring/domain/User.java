package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC) //  파라미터가 없는 디폴트 기본 생성자
@AllArgsConstructor(access = AccessLevel.PUBLIC) // 모든 필드 값을 파라미터로 받는 생성자
//@RequiredArgsConstructor(access = AccessLevel.PUBLIC) // finall 이나 @NonNull 로만 선언 되어 있는 필드만을 파라미터로 받는 생성자
@Builder
@Entity
//@Table(
//        name ="User"
//        , indexes = {@Index(columnList = "name")}
//        , uniqueConstraints = {@UniqueConstraint(columnNames = {""})}
//)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // GeneratedValue 는 자동생성 , 생성 전략은 IDENTITY (고유키)
    private Long id;

    @NonNull    // NotNull 지정
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



