package com.toyproject.board.web.controller;

import com.toyproject.board.domain.entity.User;
import com.toyproject.board.domain.service.CommentService;
import com.toyproject.board.web.argumentResolver.Login;
import com.toyproject.board.web.dto.CommentSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     *  content, writer, createAt
     *  넘어오는 commentsDto -> comments로 변경
     */
    @PostMapping("/comments")
    public String addComment(@Validated @ModelAttribute CommentSaveDto commentSaveDto,
                             BindingResult bindingResult, @Login User user) {
        if(bindingResult.hasErrors()) {
            return "redirect:/posts/" + commentSaveDto.getPostId();
        }

        log.info("[CommentController addComment] commentSaveDto : {}", commentSaveDto);
        commentService.saveComment(commentSaveDto.getPostId(), user.getId(), commentSaveDto);

        return "redirect:/posts/" + commentSaveDto.getPostId();
    }
}
