package com.toyproject.board.domain.service;

import com.toyproject.board.domain.entity.Comment;
import com.toyproject.board.domain.entity.Post;
import com.toyproject.board.domain.entity.User;
import com.toyproject.board.domain.repository.PostRepository;
import com.toyproject.board.domain.repository.UserRepository;
import com.toyproject.board.web.dto.CommentDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class CommentServiceTest {

    @Autowired private CommentService commentService;
    @Autowired private UserRepository userRepository;
    @Autowired private PostRepository postRepository;

    @Test
    void createComment(){
        //given
        User user = new User("test@test.com", "닉네임", "123");
        userRepository.save(user);

        Post post = Post.addPost("제목", "내용", user);
        postRepository.save(post);

        //when
        CommentDto commentDto = new CommentDto();
        commentDto.setContent("댓글내용");

//        Comment comment = commentService.saveComment(post.getId(), user.getId(), commentSaveDto);

//        assertThat(comment.getId()).isNotNull();
//        assertThat(comment.getContent()).isEqualTo("댓글내용");
//        assertThat(comment.getUser().getId()).isEqualTo(user.getId());
//        assertThat(comment.getPost().getId()).isEqualTo(post.getId());

    }

}