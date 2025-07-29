package com.toyproject.board.domain.service;

import com.toyproject.board.domain.entity.User;
import com.toyproject.board.domain.repository.UserRepository;
import com.toyproject.board.domain.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public User login(String userEmail, String password) {
        return userRepository.findByUserEmail(userEmail)
                .filter(user -> PasswordUtil.matches(password, user.getPassword()))
                .orElse(null);
    }
}
