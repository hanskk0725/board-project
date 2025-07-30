package com.toyproject.board.domain.service;

import com.toyproject.board.domain.entity.Comment;
import com.toyproject.board.domain.entity.Post;
import com.toyproject.board.domain.entity.User;
import com.toyproject.board.domain.repository.CommentRepository;
import com.toyproject.board.web.dto.CommentDto;
import com.toyproject.board.web.dto.CommentSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;


    public List<Comment> getAllComments(Long postId){
        return commentRepository.findByPostId(postId);
    }

    public Comment saveComment (Long postId, Long userId, CommentSaveDto commentDto) {
        Post post = postService.findPost(postId);
        User user = userService.getUserById(userId);

        Comment comment = Comment.of(user, post, commentDto.getContent());
        return  commentRepository.save(comment);
    }





}
