package com.coaching.institute.service;

import com.coaching.institute.model.ClassInfo;
import com.coaching.institute.repository.ClassRepository;
import com.coaching.institute.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private UserRepository userRepository;

    public ClassInfo createClass(ClassInfo classInfo) {
        // Validate teacher
        if (!userRepository.existsById(classInfo.getTeacherId())) {
            throw new RuntimeException("Teacher ID not found: " + classInfo.getTeacherId());
        }

        // Validate students
        for (String studentId : classInfo.getStudentIds()) {
            if (!userRepository.existsById(studentId)) {
                throw new RuntimeException("Student ID not found: " + studentId);
            }
        }

        return classRepository.save(classInfo);
    }

    public List<ClassInfo> getAllClasses() {
        return classRepository.findAll();
    }

    public Optional<ClassInfo> getClassById(String id) {
        return classRepository.findById(id);
    }

    public List<ClassInfo> getClassesByTeacher(String teacherId) {
        return classRepository.findByTeacherId(teacherId);
    }

    public List<ClassInfo> getClassesByStudent(String studentId) {
        return classRepository.findByStudentIdsContaining(studentId);
    }
}
