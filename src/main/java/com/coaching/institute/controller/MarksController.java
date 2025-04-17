package com.coaching.institute.controller;

import com.coaching.institute.dto.SubjectResultReport;
import com.coaching.institute.model.Marks;
import com.coaching.institute.service.MarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.coaching.institute.dto.SubjectResultSummary;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/marks")
@CrossOrigin(origins = "*")
public class MarksController {

    @Autowired
    private MarksService marksService;

    // ✅ Add marks
    @PostMapping("/add")
    public ResponseEntity<Marks> addMarks(@RequestBody Marks marks, Principal principal) {
        return ResponseEntity.ok(marksService.addMarks(marks, principal.getName()));
    }


    // ✅ Get all marks by student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Marks>> getByStudent(@PathVariable String studentId) {
        return ResponseEntity.ok(marksService.getMarksByStudent(studentId));
    }

    // ✅ Get all marks by class
    @GetMapping("/class/{classId}")
    public ResponseEntity<List<Marks>> getByClass(@PathVariable String classId) {
        return ResponseEntity.ok(marksService.getMarksByClass(classId));
    }

    // ✅ Get marks for a student in a specific class
    @GetMapping("/class/{classId}/student/{studentId}")
    public ResponseEntity<List<Marks>> getByClassAndStudent(
            @PathVariable String classId,
            @PathVariable String studentId
    ) {
        return ResponseEntity.ok(marksService.getMarksByClassAndStudent(classId, studentId));
    }

    // ✅ Get all marks of a student for a specific subject
    @GetMapping("/student/{studentId}/subject/{subject}")
    public ResponseEntity<List<Marks>> getBySubjectAndStudent(
            @PathVariable String studentId,
            @PathVariable String subject
    ) {
        return ResponseEntity.ok(marksService.getMarksBySubjectAndStudent(studentId, subject));
    }

    // ✅ Optional: Get marks of a student by subject & examTitle
    @GetMapping("/student/{studentId}/subject/{subject}/exam/{examTitle}")
    public ResponseEntity<List<Marks>> getByStudentSubjectAndExam(
            @PathVariable String studentId,
            @PathVariable String subject,
            @PathVariable String examTitle
    ) {
        return ResponseEntity.ok(marksService.getByStudentSubjectAndExam(studentId, subject, examTitle));
    }
    // ✅ Get result summary grouped by subject for a student
    @GetMapping("/summary/student/{studentId}")
    public ResponseEntity<List<SubjectResultSummary>> getResultSummary(@PathVariable String studentId) {
        return ResponseEntity.ok(marksService.getResultSummaryByStudent(studentId));
    }
}
