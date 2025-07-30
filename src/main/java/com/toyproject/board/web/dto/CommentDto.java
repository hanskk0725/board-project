package com.toyproject.board.web.dto;

import com.toyproject.board.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private String content;
    private String writer;
    private LocalDateTime createdAt;
    private Long postId;

    public static CommentDto fromEntity(Comment comment) {
        return CommentDto.builder()
                .content(comment.getContent())
                .writer(comment.getUser().getNickname())
                .createdAt(comment.getCreatedAt())
                .postId(comment.getPost().getId())
                .build();
    }


}
