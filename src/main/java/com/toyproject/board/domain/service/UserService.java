package com.toyproject.board.domain.service;

import com.toyproject.board.domain.entity.User;
import com.toyproject.board.domain.repository.UserRepository;
import com.toyproject.board.domain.util.PasswordUtil;
import com.toyproject.board.web.dto.UserInfoDto;
import com.toyproject.board.web.dto.UserRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public boolean isDuplicateEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail).isPresent();
    }

    public boolean isDuplicateEmailExcludingCurrentUser(String userEmail, Long userId) {
        return userRepository.findByUserEmail(userEmail)
                .filter(user -> !user.getId().equals(userId))
                .isPresent();
    }

    public void updateUserInfo(Long id, UserInfoDto dto) {
        userRepository.findById(id).ifPresent(
                user -> user.changeUserInfo(dto.getEmail(), dto.getNickname()));

    }

    public void registerUser(UserRegisterDto dto) {
        User newUser = new User(dto.getEmail(), PasswordUtil.encode(dto.getPassword()), dto.getNickname());
        userRepository.save(newUser);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
    }


}
