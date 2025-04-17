package com.coaching.institute.controller;

import com.coaching.institute.model.ClassInfo;
import com.coaching.institute.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@CrossOrigin(origins = "*")
public class ClassController {

    @Autowired
    private ClassService classService;

    // Add a new class
    @PostMapping("/create")
    public ResponseEntity<ClassInfo> createClass(@RequestBody ClassInfo classInfo) {
        return ResponseEntity.ok(classService.createClass(classInfo));
    }

    // Get all classes
    @GetMapping("/all")
    public ResponseEntity<List<ClassInfo>> getAllClasses() {
        return ResponseEntity.ok(classService.getAllClasses());
    }

    // Get classes by teacher
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<ClassInfo>> getByTeacher(@PathVariable String teacherId) {
        return ResponseEntity.ok(classService.getClassesByTeacher(teacherId));
    }

    // Get classes by student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ClassInfo>> getByStudent(@PathVariable String studentId) {
        return ResponseEntity.ok(classService.getClassesByStudent(studentId));
    }
}
