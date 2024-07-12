package com.lec.spring.repository;

import com.lec.spring.domain.Post;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 스프링 context 를 로딩하여 테스트에 사용
class PostRepositoryTest2 {


    // MyBatis 가 생성한 구현체들이 담겨 있는 SqlSession 객체
    // 기본적으로 스프링에 빈 생성되어있어서 주입 받을수 있다
    @Autowired
    private SqlSession sqlSession;

    @Test
    void test0() {
        PostRepository postRepository = sqlSession.getMapper(PostRepository.class);

        System.out.println("[최초]");
        postRepository.findAll().forEach(System.out::println);

        Post post = Post.builder()
                .user("최영재")
                .subject("재혁")
                .content("고고고고")
                .build();
        int result = postRepository.save(post);

        System.out.println("save 결과는 " + result);

        System.out.println("[신규 생성후]");
        postRepository.findAll().forEach(System.out::println);
    }
}