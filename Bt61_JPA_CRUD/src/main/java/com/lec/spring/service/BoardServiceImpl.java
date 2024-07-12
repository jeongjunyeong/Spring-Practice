package com.lec.spring.service;

import com.lec.spring.domain.Post;
import com.lec.spring.dto.PostDto;
import com.lec.spring.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final PostRepository postRepository;

    // a먼저 받아준다.

    @Override
    public Post write(PostDto postDto) {
        postDto.setRegDate(LocalDateTime.now());
        return postRepository.save(postDto.toEntity());
    }

    @Override
    @Transactional
    public Post detail(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        optionalPost.ifPresent(Post::increaseViewCnt);
        return optionalPost.get();
    }

    @Override
    public List<Post> list() {
        return postRepository.findAll();
    }

    @Override
    public Post selectById(Long id) {
        postRepository.findById(id);
        return postRepository.findById(id).get();
    }

    @Override
    public int update(Post post) {
        postRepository.save(post);
        return 1;
    }

    @Override
    public int deleteById(Long id) {
        postRepository.deleteById(id);  // selete를 하고 delete를 한다.
        return 1;
    }

    // 여기부분 다시
//    @Override
//    public Long viewCnt {
//        postRepository.count();
//    }

} // end Service
