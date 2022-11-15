package com.nhnacademy.score.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
@Controller
public class LoginController {
    private static String admin = "admin";
    private static String password = "12345";
    @GetMapping("/login")
    public String login( HttpServletRequest request,
                        Model model) {
        HttpSession session = request.getSession(false);
        if (!Objects.isNull(session)){
            if (!Objects.isNull(session.getAttribute("login"))) {
                return "thymeleaf/loginSuccess";
            }
        }
        return "thymeleaf/loginForm";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("id") String id,
                          @RequestParam("pwd") String pwd,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          ModelMap modelMap) {
        if(id.equals(admin) && pwd.equals(password)){
            HttpSession session = request.getSession(true);

            Cookie cookie = new Cookie("SESSION", session.getId());
            response.addCookie(cookie);

            modelMap.put("id", admin);
            session.setAttribute("login", admin);
            return "thymeleaf/loginSuccess";
        }else{
            return "thymeleaf/loginForm";
        }
    }

}
