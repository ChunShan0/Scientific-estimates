package com.example.evaluation.mapper;

import com.example.evaluation.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CourseMapper {
    List<Course> findAll();
    List<Course> findByTeacherId(@Param("teacherId") Long teacherId);
    Course findById(@Param("id") Long id);
    int insert(Course course);
    int update(Course course);
    int deleteById(@Param("id") Long id);
} 