package com.example.evaluation.service;

import com.example.evaluation.entity.Evaluation;
import java.util.List;

public interface EvaluationService {
    List<Evaluation> getEvaluationsByCourseId(Long courseId);
    List<Evaluation> getEvaluationsByEvaluatorId(Long evaluatorId);
    Evaluation getEvaluationById(Long id);
    Evaluation createEvaluation(Evaluation evaluation);
    Evaluation updateEvaluation(Evaluation evaluation);
    void deleteEvaluation(Long id);
} 