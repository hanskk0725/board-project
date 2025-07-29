package com.toyproject.board.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Post {

    @Id @GeneratedValue
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt;


    public static Post addPost(String title, String content, User user) {
        Post post = new Post();
        post.title = title;
        post.content = content;
        post.user = user;

        return post;
    }

    public String getWriterName(){
        return user.getNickname();
    }

    @PrePersist
    private void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public void changePost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
