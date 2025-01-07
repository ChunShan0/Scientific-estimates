package com.example.evaluation.service;

import com.example.evaluation.entity.Course;
import java.util.List;

public interface CourseService {
    List<Course> getAllCourses();
    List<Course> getCoursesByTeacherId(Long teacherId);
    Course getCourseById(Long id);
    Course createCourse(Course course);
    Course updateCourse(Course course);
    void deleteCourse(Long id);
} 