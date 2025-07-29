package com.toyproject.board.web.controller;

import com.toyproject.board.domain.entity.User;
import com.toyproject.board.domain.service.UserService;
import com.toyproject.board.web.argumentResolver.Login;
import com.toyproject.board.web.dto.UserInfoDto;
import com.toyproject.board.web.dto.UserRegisterDto;
import com.toyproject.board.web.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/add")
    public String registerForm(@ModelAttribute("user") UserRegisterDto dto) {
        return "users/registerForm";
    }

    @PostMapping("/add")
    public String register(@Validated @ModelAttribute("user") UserRegisterDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "users/registerForm";
        }

        if (userService.isDuplicateEmail(dto.getEmail())) {
            bindingResult.reject("duplicate.email", "This email address is already in use");
            return "users/registerForm";
        }

        userService.registerUser(dto);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String myPage(@PathVariable("id") Long id, @Login User login, Model model) {
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/";
        }

        // 세션이 아니라 항상 DB에서 사용자 정보 가져오기
        User userFromDb = userService.getUserById(id);

        UserInfoDto userInfoDto = new UserInfoDto(userFromDb.getUserEmail(), userFromDb.getNickname());
        model.addAttribute("user", userInfoDto);
        return "users/userDetails";
    }

    @GetMapping("/edit/{id}")
    public String userEdit(@PathVariable("id") Long id, @Login User login ,Model model) {
        if(login == null || !login.getId().equals(id)) {
            return "redirect:/";
        }

        User userFromDb = userService.getUserById(id);

        UserInfoDto userInfoDto = new UserInfoDto(userFromDb.getUserEmail(), userFromDb.getNickname());
        model.addAttribute("user", userInfoDto);
        return "users/editUser";
    }

    @PostMapping("/edit/{id}")
    public String userEdit(@PathVariable("id") Long id,
                           @Validated @ModelAttribute("user") UserInfoDto dto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           @Login User loginUser,
                           HttpServletRequest request) {

        if (loginUser == null || !loginUser.getId().equals(id)) {
            log.info("[Session not valid, user = {}]", loginUser);
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            log.info("user edit error: {}", bindingResult);
            return "users/editUser";
        }

        if(userService.isDuplicateEmailExcludingCurrentUser(dto.getEmail(), id)) {
            bindingResult.reject("duplicate.email", "This email address is already in use");
            log.info("[이메일 중복 검증 실패]");
            return "users/editUser";
        }

        // 1. 사용자 정보 업데이트
        userService.updateUserInfo(id, dto);

        // 2. 갱신된 사용자 정보를 DB에서 다시 조회
        User updatedUser = userService.getUserById(id);

        // 3. 세션의 사용자 정보 갱신
        if (updatedUser != null) {
            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_USER, updatedUser);
        }

        redirectAttributes.addFlashAttribute("status", "success");
        return "redirect:/users/" + id;
    }


}
