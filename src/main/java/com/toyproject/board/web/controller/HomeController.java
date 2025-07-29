package com.toyproject.board.web.controller;

import com.toyproject.board.domain.entity.User;
import com.toyproject.board.web.argumentResolver.Login;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@Login User user, Model model) {
        if (user == null) {
            return "home/home";
        }

        model.addAttribute("user", user);
        return "home/loginHome";
    }


}
