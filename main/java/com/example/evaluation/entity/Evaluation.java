package com.example.evaluation.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Evaluation {
    private Long id;
    private Long courseId;
    private Long evaluatorId;
    private Integer teachingScore;    // 教学水平评分
    private Integer contentScore;     // 课程内容评分
    private Integer interactionScore; // 师生互动评分
    private String comments;          // 评价意见
    private String status;            // 草稿、已提交
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 