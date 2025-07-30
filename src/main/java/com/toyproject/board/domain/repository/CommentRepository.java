package com.toyproject.board.domain.repository;

import com.toyproject.board.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c join fetch c.post p where p.id = :postId")
    List<Comment> findByPostId(Long postId);
}
