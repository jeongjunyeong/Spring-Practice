package com.lec.spring.domain;

import com.lec.spring.listener.Auditable;
import com.lec.spring.listener.UserEntityListener;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
        name="T_USER" // DB 테이블 명
        , indexes = {@Index(columnList = "name")} // 컬럼에 대한 index 생성
        , uniqueConstraints = {@UniqueConstraint(columnNames = {"email","name"})}) //unique
// JPA는 기본적으로 PK가 있어야 한다. --> ID를 지정해 준다
// name을 직접적으로 지정해 줄 수 있다.
// Entity 객체 지정
@EntityListeners(value = {UserEntityListener.class})
public class User extends BaseEntity {


    @Id // PK 지정.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 마치 Mysql 의 Auto_increment 와 같은 동작 수행
    private Long id;

    @NonNull
    private String name;
    @NonNull
    @Column(unique = true)
    private String email;
    // name 하고 email은 null로 두어서는 안된다.

//    @Column(
//            name = "crtdat",
//            nullable = false)
//    @Column(updatable = false)
//    @CreatedDate // AuditingEntityListener 가 Listener 로 적용시 사용
//    // 업데이트 관련 진행은 하지 않는다.
//    private LocalDateTime createdAt;

//    @Column(insertable = false)
//    // insert 관련 진행은 하지 않는다.
//    @LastModifiedDate // AuditingEntityListener 가 Listener 로 적용시 사용
//    private LocalDateTime updatedAt;

    // User:Adress => 1:N
//    @OneToMany(fetch = FetchType.EAGER)

    // User가 로딩될 때 같이 로딩해줘라
//    private List<Address> address;


    @Transient  // jakarta.persistence  DB 에 반영 안하는 필드 속성.  영속성을 부여하지 않는다.
    private String testData; // test_data


    @Enumerated(EnumType.STRING)
    // 유지보수하기 위해서는 EnumType 속성에 ORDINAL(디폴트) 과 STRING 이 있다.
    //ORDINAL : enum 값 순서에 따른 정수값으로 다룸 (0-base)
    private Gender gender; // enum 타입으로 선언해 주었죠?

    // User: UserHIstory = 1:N
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id" // Entity 가 어떤 칼럼으로 join 하게 될지 지정해 준다.
        // name = "user_id"     : join 할 컬럼명 지정가능
        // UserHistory 의 user_id 란 컬럼으로 join
            , insertable = false, updatable = false
            // User 에서 userHistory의 값을 추가, 수정하지 못하도록 하기 위해
    )
    @ToString.Exclude
    private List<UserHistory>userHistories = new ArrayList<>(); // NPE 방지


    // User:Review = 1:N
    @OneToMany
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private List<Review>reviews = new ArrayList<>();


//    @PrePersist
//    // 이 메소드는 insert 동작전에 시행된다.
//    public void prePersist(){
//        System.out.println(">> prePersist");
//        this.createdAt = LocalDateTime.now();
//        this.updatedAt = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    // 이 메소드는 update 동작전에 시행된다.
//    public void preUpdate() {
//        System.out.println(">>> preUpdate");
//        this.updatedAt = LocalDateTime.now();
//    }
//
//    // 이 메소드는 remove 동작전에 시행된다.
//    @PreRemove
//    public void preRemove() {
//        System.out.println(">>> preRemove");
//    }
//
//    // 이 메소드는 insert 동작 후에 시행된다.
//    @PostPersist
//    public void postPersist() {
//        System.out.println(">>> postPersist");
//    }
//
//    @PostUpdate
//    public void postUpdate() {
//        System.out.println(">>> postUpdate");
//    }
//
//    @PostRemove
//    public void postRemove() {
//        System.out.println(">>> postRemove");
//    }
//
//    @PostLoad
//    public void postLoad() {
//        System.out.println(">>> postLoad");
//    }


    // EMbedded 예제
    // Embed 없이 주소 다루기
    private String city;
    private String district;
    private String detail;
    private String zipCode;




}
