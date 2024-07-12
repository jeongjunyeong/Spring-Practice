package com.lec.spring.listener;

import com.lec.spring.domain.User;
import com.lec.spring.domain.UserHistory;
import com.lec.spring.repository.UserHistoryRepository;
import com.lec.spring.support.BeanUtils;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;


// ☆ Entity Listener 는 spring Bean을 주입 받지 못한다!!
//@Component
public class UserEntityListener {

    // ☆ Entity Listener 는 spring Bean을 주입 받지 못한다!!
//    @Autowired
//    private UserHistoryRepository userHistoryRepository;

    @PostUpdate
    @PostPersist
    public void addUserHistory(Object o){
        System.out.println(">> prePersistAndPreUpdate addUserHistory");

        // 스프링 bean 객체 주입
        UserHistoryRepository userHistoryRepository = BeanUtils.getBean(UserHistoryRepository.class);

        User user = (User) o;
        // UserHistory 에 UPDATE 될 User 정보를 담아서 저장 (INSERT)
        UserHistory userHistory = new UserHistory();
//        userHistory.setUserId(user.getId());
        userHistory.setName(user.getName());
        userHistory.setEmail(user.getEmail());
        userHistory.setUser(user);

        userHistoryRepository.save(userHistory); //Insert
    }
}
