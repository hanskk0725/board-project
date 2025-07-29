package com.toyproject.board.web.advice;

import com.toyproject.board.domain.entity.User;
import com.toyproject.board.web.session.SessionConst;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("loginUser")
    public User setUser(HttpSession session) {
        return (User) session.getAttribute(SessionConst.LOGIN_USER);
    }
}
