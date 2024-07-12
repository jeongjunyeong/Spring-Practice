package com.lec.spring.repository;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AttachmentRepositoryTest {

    @Autowired
    private SqlSession sqlSession;

    @Test
    void attachmentTest1() {
        AttachmentRepository repository = sqlSession.getMapper(AttachmentRepository.class);

        var list = repository.findByPost(1L);
        list.forEach(System.out::println);


        System.out.println();
        System.out.println(repository.findById(1L));

        System.out.println();
        list = repository.findByIds(new Long[]{1L, 3L, 5L});
        list.forEach(System.out::println);


        List<Map<String, Object>> fileList = List.of(
                Map.of("sourcename", "최영재", "filename", "최영재file"),
                Map.of("sourcename", "이재혁", "filename","이재혁file")
        );

        int cnt = repository.insert(fileList,1L);
        System.out.println(cnt + " 개의 INSERT");


        list = repository.findByPost(1L);
        list.forEach(System.out::println);


    }


    @Test
    void attachmentTest2() {
        AttachmentRepository repository = sqlSession.getMapper(AttachmentRepository.class);

        int cnt = repository.deleteByIds(new Long[]{9L, 10L});
        System.out.println(cnt + "개의 delete");

        var list = repository.findByPost(1L);
        list.forEach(System.out::println);


    }
}