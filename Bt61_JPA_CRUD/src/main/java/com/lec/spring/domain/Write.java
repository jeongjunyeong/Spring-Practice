package com.lec.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


// 시나리오

// id 값은 반드시 숫자, 비엇있으면 안된다.
// name 값은 반드시 입력, 비어있어면 안된다.

//
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Write {
    int id;
    String name;
    String subject;
    // Validation failed for object='write'. Error count: 1 <-- 바인딩 하는 과정에서 생기는 에러
}
