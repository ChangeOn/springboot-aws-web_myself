package com.myself.book.springboot.service;

import com.myself.book.springboot.domain.posts.Posts;
import com.myself.book.springboot.domain.posts.PostsRepository;
import com.myself.book.springboot.dto.PostsResponseDto;
import com.myself.book.springboot.dto.PostsSaveRequestDto;
import com.myself.book.springboot.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts =  postsRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없다는데용. id=" + id ));
        /*        if (value != null) { return value; } else { throw exceptionSupplier.get(); }      */

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public void delete(Long id) {
        Posts posts =  postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없다요~. id=" + id ));

        postsRepository.delete(posts);

    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없다요~. id=" + id ));

        return new PostsResponseDto(entity);
    }

}
