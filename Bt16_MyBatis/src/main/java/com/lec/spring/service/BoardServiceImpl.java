package com.lec.spring.service;

import com.lec.spring.domain.Post;
import com.lec.spring.repository.PostRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private PostRepository postRepository;

    @Autowired // 생성자가 하나인 경우에는 없어도 주입이 가능하다.
    public BoardServiceImpl(SqlSession sqlSession) { //myBatis 가 생성한 SQlLsession 빈(bean) 객체 주입
        postRepository = sqlSession.getMapper(PostRepository.class);
        System.out.println("BoardServiceImpl() 생성");
    }
    @Override
    public int write(Post post) {
        return postRepository.save(post); // 저장을 성공하면 1을 리턴
    }

    @Override
    @Transactional
    public Post detail(Long id) {
        postRepository.incViewCnt(id);  // 조회수 증가 (UPDATE)
        Post post = postRepository.findById(id);
        return post;
    }

    @Override
    public List<Post> list() {
        return postRepository.findAll();
    }

    @Override
    public Post selectById(Long id) {
        Post post = postRepository.findById(id);
        return post;
    }

    @Override
    public int update(Post post) {
        return postRepository.update(post);
    }

    @Override
    public int deleteById(Long id) {
        int result = 0;

        Post post = postRepository.findById(id);  // 존재하는 데이터인지 확인
        if(post != null){  // 존재한다면 삭제 진행
            result = postRepository.delete(post);
        }

        return result;
    }



} // end Service
