package com.toyproject.board.domain.service;

import com.toyproject.board.domain.entity.Post;
import com.toyproject.board.domain.query.PostSearchCond;
import com.toyproject.board.domain.repository.PostRepository;
import com.toyproject.board.web.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void modifiedPost(PostDto dto, Long postId) {
        postRepository.findById(postId).ifPresent(post -> post.changePost(dto.getTitle(), dto.getContent()));
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public Page<Post> searchPosts(PostSearchCond cond, Pageable pageable) {
        return postRepository.search(cond, pageable);
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }

    public Post findPost(Long postId) {
        return postRepository
                .findPostWithUserById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
    }


}
