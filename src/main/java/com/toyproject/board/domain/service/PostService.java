package com.toyproject.board.domain.service;

import com.toyproject.board.domain.entity.Post;
import com.toyproject.board.domain.query.PostSearchCond;
import com.toyproject.board.domain.repository.CommentRepository;
import com.toyproject.board.domain.repository.PostRepository;
import com.toyproject.board.web.dto.CommentDto;
import com.toyproject.board.web.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

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
        log.info("[PostService - findPost Method ] [Post Id: {} ]", postId);
        return postRepository
                .findPostWithUserById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
    }

    public PostDto getPostWithComments(Long postId, String username) {
        Post post = findPost(postId);

        List<CommentDto> comments = commentRepository.findByPostId(postId)
                .stream()
                .map(CommentDto::fromEntity)
                .collect(Collectors.toList());

        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .writer(username)
                .createdAt(post.getCreatedAt())
                .comments(comments)
                .userId(post.getUser().getId())
                .build();
    }


}
