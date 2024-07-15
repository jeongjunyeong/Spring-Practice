package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity(name = "t8_user")
public class User extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Transient // Entity 에 반영 안함.!! 검증하는 것이지 데이터 베이스에 넣는게 아니였다.
    @ToString.Exclude // toString() 에서 제외
    @JsonIgnore
    private String re_password;

    @Column(nullable = false)
    private String name; // 회원 이름
    private String email; // 이메일


    // fetch 기본값
    // @OneToMany, @ManyToMany -> FetchType.Lazy
    // @ManyToOne, @OneToOne -> FetchType.EAGER
    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    @Builder.Default
    @JsonIgnore
    private List<Authority> authorities = new ArrayList<>();

    public void addAuthority(Authority... authorities) {
        Collections.addAll(this.authorities, authorities);
    }

    //OAuth2 client
    private String provider;
    private String providerId;
}
