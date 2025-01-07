package com.example.evaluation.controller;

import com.example.evaluation.common.Result;
import com.example.evaluation.entity.Course;
import com.example.evaluation.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public Result<List<Course>> getAllCourses() {
        return Result.success(courseService.getAllCourses());
    }

    @GetMapping("/teacher/{teacherId}")
    public Result<List<Course>> getCoursesByTeacherId(@PathVariable Long teacherId) {
        return Result.success(courseService.getCoursesByTeacherId(teacherId));
    }

    @GetMapping("/{id}")
    public Result<Course> getCourseById(@PathVariable Long id) {
        return Result.success(courseService.getCourseById(id));
    }

    @PostMapping
    public Result<Course> createCourse(@RequestBody Course course) {
        return Result.success(courseService.createCourse(course));
    }

    @PutMapping("/{id}")
    public Result<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        course.setId(id);
        return Result.success(courseService.updateCourse(course));
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return Result.success(null);
    }
} 