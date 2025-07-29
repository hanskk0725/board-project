package com.toyproject.board.web.controller;

import com.toyproject.board.domain.entity.Post;
import com.toyproject.board.domain.entity.User;
import com.toyproject.board.domain.query.PostSearchCond;
import com.toyproject.board.domain.repository.PostRepository;
import com.toyproject.board.domain.service.PostService;
import com.toyproject.board.web.argumentResolver.Login;
import com.toyproject.board.web.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public String home(Model model,@ModelAttribute PostSearchCond searchCond,
                       @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Post> posts = postService.searchPosts(searchCond, pageable);
        model.addAttribute("posts", posts);
        model.addAttribute("searchCond", searchCond);
        return "posts/posts";
    }

    @GetMapping("/add")
    public String addPostForm(@ModelAttribute("post") PostDto postSaveDto,
                              @Login User user, Model model) {
        if (user == null) {
            return "redirect:/";
        }

        model.addAttribute("user", user);
        return "posts/addPostForm";
    }

    @PostMapping("/add")
    public String addPost(@Validated @ModelAttribute("post") PostDto postSaveDto,
                          BindingResult bindingResult,
                          @Login User user, Model model) {

        if (bindingResult.hasErrors()) {
            log.info("[addPost bindingResult error={}]", bindingResult);
            return "posts/addPostForm";
        }

        Post post = Post.addPost(postSaveDto.getTitle(), postSaveDto.getContent(), user);
        postService.savePost(post);

        log.info("[Post Save] title: {}, content: {}", post.getTitle(), post.getContent());
        model.addAttribute("nickname", user.getNickname());
        return "redirect:/posts";
    }

    @GetMapping("/{id}")
    public String post(@PathVariable Long id, Model model) {
        Post post = postService.findPost(id);
        model.addAttribute("post", post);
        return "posts/post";
    }

    @GetMapping("/edit/{id}")
    public String editPostForm(@PathVariable Long id, Model model, @Login User user) {
        Post post = postService.findPost(id);

        if (!post.getUser().getId().equals(user.getId())) {
            return "redirect:/";
        }

        PostDto postDto = new PostDto(post.getTitle(), post.getContent());
        model.addAttribute("post", postDto);

        return "posts/editPostForm";
    }

    @PostMapping("/edit/{id}")
    public String editPost(@ModelAttribute("post") PostDto postDto, @PathVariable Long id) {
        postService.modifiedPost(postDto, id);

        return "redirect:/posts/" + id;
    }

    @PostMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }
}
