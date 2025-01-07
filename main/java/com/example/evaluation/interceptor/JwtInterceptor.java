package com.example.evaluation.interceptor;

import com.example.evaluation.exception.BusinessException;
import com.example.evaluation.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {
    private final JwtUtil jwtUtil;

    public JwtInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new BusinessException("未登录");
        }

        token = token.substring(7);
        Claims claims = jwtUtil.parseToken(token);
        
        // 将用户信息存入request
        request.setAttribute("userId", claims.get("userId"));
        request.setAttribute("username", claims.get("username"));
        request.setAttribute("role", claims.get("role"));
        
        return true;
    }
} 