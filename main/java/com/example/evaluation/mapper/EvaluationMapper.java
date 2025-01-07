package com.example.evaluation.mapper;

import com.example.evaluation.entity.Evaluation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface EvaluationMapper {
    List<Evaluation> findByCourseId(@Param("courseId") Long courseId);
    List<Evaluation> findByEvaluatorId(@Param("evaluatorId") Long evaluatorId);
    Evaluation findById(@Param("id") Long id);
    int insert(Evaluation evaluation);
    int update(Evaluation evaluation);
    int deleteById(@Param("id") Long id);
} 