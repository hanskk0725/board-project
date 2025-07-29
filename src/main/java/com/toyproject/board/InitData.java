package com.toyproject.board;

import com.toyproject.board.domain.entity.Post;
import com.toyproject.board.domain.entity.User;
import com.toyproject.board.domain.repository.PostRepository;
import com.toyproject.board.domain.repository.UserRepository;
import com.toyproject.board.domain.util.PasswordUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitData {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @PostConstruct
    public void init() {
        User tester1 = userRepository.save(new User("test1@test", PasswordUtil.encode("123"), "테스터_1"));
        User tester2 = userRepository.save(new User("test2@test", PasswordUtil.encode("123"), "테스터_2"));

        for (int j = 0; j < 20; j++) {
            postRepository.save(Post.addPost("테스트 제목 " + j, "테스트 입니다 " + j, tester1));
        }

        for (int j = 0; j < 20; j++) {
            postRepository.save(Post.addPost("테스트 제목 " + j, "테스트 입니다 " + j, tester2));
        }
    }
}
