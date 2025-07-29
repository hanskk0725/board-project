package com.toyproject.board.web.controller;

import com.toyproject.board.domain.entity.User;
import com.toyproject.board.domain.service.LoginService;
import com.toyproject.board.web.argumentResolver.Login;
import com.toyproject.board.web.dto.LoginUserDto;
import com.toyproject.board.web.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("user") LoginUserDto dto, @Login User user) {

        if (user != null) {
            log.info("User already logged in, user = {}", user);
            return "redirect:/";
        }

        return "users/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("user") LoginUserDto dto,
                        BindingResult bindingResult, HttpServletRequest request,
                        @RequestParam(defaultValue = "/") String redirectURL) {

        if (bindingResult.hasErrors()) {
            log.info("[bindingResult = {}]", bindingResult.getFieldError());
            return "users/loginForm";
        }

        User findUser = loginService.login(dto.getUserEmail(), dto.getPassword());

        if (findUser == null) {
            bindingResult.reject("login.failed", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "users/loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, findUser);

        return "redirect:" + redirectURL;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }
}
