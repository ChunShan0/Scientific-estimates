package com.example.evaluation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.evaluation.mapper")
public class TeachingEvaluationApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeachingEvaluationApplication.class, args);
    }
} 