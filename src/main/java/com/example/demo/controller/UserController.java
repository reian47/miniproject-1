package com.example.demo.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLOutput;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    // http://localhost:8080/userRegForm
    // classpath:/templates/userRegForm.html.html
    @GetMapping("/userRegForm")
    public String userRegForm(){
        return "userRegForm";
    }

    /**
     * 회원 정보를 등록한다.
     * @param name
     * @param email
     * @param password
     * @return
     */
    @PostMapping("/userReg")
    public String userReg(
        @RequestParam("name") String name,
        @RequestParam("email") String email,
        @RequestParam("password") String password
     ){
            System.out.println("name : " + name);
            System.out.println("email : " + email);
            System.out.println("password : " + password);

            userService.addUser(name, email, password);

            //  어떤 기능이 필요한지 미리 알수 잇다.
            //  회원 정보를 저장한다. 동사는 메소드, 메소드만 선언하고 있는것은 인터페이스.

            return "redirect:/welcome"; // 브라우저에게 자동으로 http://localhost.welcome 으로 이동
    }
    // http://localhost:8080/welcome
    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @GetMapping("/loginform")
    public String logingform(){
        return "loginform";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ){
        // email에 해당하는 회원 정보를 읽어온후
        // 아이디 암호가 맞다면 세션이 회원정보를 저장한다
        System.out.println("email : " + email);
        System.out.println("password : " + password);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(){
        // 세션에서 회원정보를 삭제한다.
        return "redirect:/";
    }
}
