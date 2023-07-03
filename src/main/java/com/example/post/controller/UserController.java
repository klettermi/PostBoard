package com.example.post.controller;

import com.example.post.dto.LoginRequestDto;
import com.example.post.dto.SignReqeustDto;
import com.example.post.dto.StatusCodeDto;
import com.example.post.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/user/signup")
    public ResponseEntity<StatusCodeDto> signup(@RequestBody @Valid SignReqeustDto signReqeustDto){
        userService.signup(signReqeustDto);
        return ResponseEntity.ok(new StatusCodeDto(HttpStatus.OK.value(), "회원가입 성공!"));
    }

    @PostMapping("/user/login")
    public ResponseEntity<StatusCodeDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        userService.login(loginRequestDto, response);
        return ResponseEntity.ok(new StatusCodeDto(HttpStatus.OK.value(),"로그인 성공!"));
    }
}
