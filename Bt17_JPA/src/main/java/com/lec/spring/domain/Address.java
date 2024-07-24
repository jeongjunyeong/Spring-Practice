package com.lec.spring.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {
//    @Id
//    private Long id;

    //Embedded 예제
    //Embed 없이 주소 다루기
    private String city;
    private String district;
    @Column(name = "address_ditail")
    private String detail;
    private String zipCode;



}
