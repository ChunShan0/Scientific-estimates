package com.example.evaluation.service.impl;

import com.example.evaluation.entity.Evaluation;
import com.example.evaluation.mapper.EvaluationMapper;
import com.example.evaluation.service.EvaluationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EvaluationServiceImpl implements EvaluationService {
    private final EvaluationMapper evaluationMapper;

    public EvaluationServiceImpl(EvaluationMapper evaluationMapper) {
        this.evaluationMapper = evaluationMapper;
    }

    @Override
    public List<Evaluation> getEvaluationsByCourseId(Long courseId) {
        return evaluationMapper.findByCourseId(courseId);
    }

    @Override
    public List<Evaluation> getEvaluationsByEvaluatorId(Long evaluatorId) {
        return evaluationMapper.findByEvaluatorId(evaluatorId);
    }

    @Override
    public Evaluation getEvaluationById(Long id) {
        return evaluationMapper.findById(id);
    }

    @Override
    @Transactional
    public Evaluation createEvaluation(Evaluation evaluation) {
        evaluation.setStatus("草稿");
        evaluationMapper.insert(evaluation);
        return evaluation;
    }

    @Override
    @Transactional
    public Evaluation updateEvaluation(Evaluation evaluation) {
        evaluationMapper.update(evaluation);
        return evaluation;
    }

    @Override
    @Transactional
    public void deleteEvaluation(Long id) {
        evaluationMapper.deleteById(id);
    }
} 