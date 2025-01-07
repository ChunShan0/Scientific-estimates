package com.example.evaluation.controller;

import com.example.evaluation.common.Result;
import com.example.evaluation.service.UserService;
import com.example.evaluation.vo.LoginVO;
import com.example.evaluation.vo.UserVO;
import com.example.evaluation.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Result<UserVO> login(@Valid @RequestBody LoginVO loginVO) {
        return Result.success(userService.login(loginVO));
    }

    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader("Authorization") String token) {
        // 实际项目中，可以将token加入黑名单
        // 这里简单返回成功即可
        return Result.success(null);
    }

    @PostMapping("/password")
    public Result<Void> updatePassword(@RequestAttribute Long userId,
                                     @RequestParam String oldPassword,
                                     @RequestParam String newPassword) {
        userService.updatePassword(userId, oldPassword, newPassword);
        return Result.success(null);
    }
}