package com.example.evaluation.service.impl;

import com.example.evaluation.entity.Course;
import com.example.evaluation.mapper.CourseMapper;
import com.example.evaluation.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseMapper courseMapper;

    public CourseServiceImpl(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseMapper.findAll();
    }

    @Override
    public List<Course> getCoursesByTeacherId(Long teacherId) {
        return courseMapper.findByTeacherId(teacherId);
    }

    @Override
    public Course getCourseById(Long id) {
        return courseMapper.findById(id);
    }

    @Override
    @Transactional
    public Course createCourse(Course course) {
        course.setStatus("未评估");
        courseMapper.insert(course);
        return course;
    }

    @Override
    @Transactional
    public Course updateCourse(Course course) {
        courseMapper.update(course);
        return course;
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        courseMapper.deleteById(id);
    }
} 