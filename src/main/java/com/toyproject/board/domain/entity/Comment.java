package com.toyproject.board.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    private LocalDateTime createdAt;

    @PrePersist
    private void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public static Comment of(User user, Post post, String content){
        Comment comment = new Comment();
        comment.post = post;
        comment.user = user;
        comment.content = content;
        return comment;
    }

}
