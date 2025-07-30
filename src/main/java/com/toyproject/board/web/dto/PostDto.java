package com.toyproject.board.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotEmpty
    private String writer;

    private LocalDateTime createdAt;

    private List<CommentDto> comments;

    private Long userId;
}
