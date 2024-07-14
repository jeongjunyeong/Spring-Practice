package com.lec.spring.repository;

import com.lec.spring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


// JPA 상속받기
@Repository
// 여기가 repository 단임을 명시하는 것

public interface UserRepository extends JpaRepository<User, Long> {

}
