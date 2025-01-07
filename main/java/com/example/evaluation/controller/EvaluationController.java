package com.example.evaluation.controller;

import com.example.evaluation.common.Result;
import com.example.evaluation.entity.Evaluation;
import com.example.evaluation.service.EvaluationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController {
    private final EvaluationService evaluationService;

    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping("/course/{courseId}")
    public Result<List<Evaluation>> getEvaluationsByCourseId(@PathVariable Long courseId) {
        return Result.success(evaluationService.getEvaluationsByCourseId(courseId));
    }

    @GetMapping("/evaluator/{evaluatorId}")
    public Result<List<Evaluation>> getEvaluationsByEvaluatorId(@PathVariable Long evaluatorId) {
        return Result.success(evaluationService.getEvaluationsByEvaluatorId(evaluatorId));
    }

    @GetMapping("/{id}")
    public Result<Evaluation> getEvaluationById(@PathVariable Long id) {
        return Result.success(evaluationService.getEvaluationById(id));
    }

    @PostMapping
    public Result<Evaluation> createEvaluation(@RequestBody Evaluation evaluation) {
        return Result.success(evaluationService.createEvaluation(evaluation));
    }

    @PutMapping("/{id}")
    public Result<Evaluation> updateEvaluation(@PathVariable Long id, @RequestBody Evaluation evaluation) {
        evaluation.setId(id);
        return Result.success(evaluationService.updateEvaluation(evaluation));
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteEvaluation(@PathVariable Long id) {
        evaluationService.deleteEvaluation(id);
        return Result.success(null);
    }
} 