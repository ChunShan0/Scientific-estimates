package com.example.evaluation.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Course {
    private Long id;
    private String courseName;
    private String courseCode;
    private String semester;
    private Long teacherId;
    private String status;  // 未评估、评估中、已评估
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 