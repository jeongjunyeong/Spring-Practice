package com.lec.spring.dto;

import com.lec.spring.domain.Post;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    String user;
    String subject;
    String content;
    LocalDateTime regDate;

    public Post toEntity() {
        return Post.builder()
                .user(user).subject(subject).content(content).regDate(regDate)
                .build();
    }
}
