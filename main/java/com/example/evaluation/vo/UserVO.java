package com.example.evaluation.vo;

import lombok.Data;

@Data
public class UserVO {
    private Long id;
    private String username;
    private String role;
    private String realName;
    private String email;
    private String phone;
    private String token;
} 