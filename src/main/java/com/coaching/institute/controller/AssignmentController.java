package com.coaching.institute.controller;

import com.coaching.institute.model.Assignment;
import com.coaching.institute.model.AssignmentSubmission;
import com.coaching.institute.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = "*")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    // Upload assignment (teacher)
    @PostMapping("/upload")
    public ResponseEntity<Assignment> uploadAssignment(@RequestBody Assignment assignment) {
        return ResponseEntity.ok(assignmentService.uploadAssignment(assignment));
    }

    // Get by class
    @GetMapping("/class/{classId}")
    public ResponseEntity<List<Assignment>> getByClass(@PathVariable String classId) {
        return ResponseEntity.ok(assignmentService.getAssignmentsByClass(classId));
    }

    // Get by teacher
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Assignment>> getByTeacher(@PathVariable String teacherId) {
        return ResponseEntity.ok(assignmentService.getAssignmentsByTeacher(teacherId));
    }

    // Student submits assignment file
    @PostMapping("/{assignmentId}/submit")
    public ResponseEntity<Assignment> submitAssignment(
            @PathVariable String assignmentId,
            @RequestParam("studentId") String studentId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        // Generate upload folder path relative to project root
        String rootPath = System.getProperty("user.dir");
        String uploadsDir = rootPath + File.separator + "uploads" + File.separator + "assignments" + File.separator;

        // Create folder if it doesn't exist
        File uploadDir = new File(uploadsDir);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        // Unique filename
        String originalName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = System.currentTimeMillis() + "_" + originalName;
        String fullFilePath = uploadsDir + uniqueFileName;

        // Save the file
        file.transferTo(new File(fullFilePath));

        // URL to access the file (frontend use)
        String fileUrl = "http://localhost:8080/uploads/assignments/" + uniqueFileName;

        // Build submission object
        AssignmentSubmission submission = new AssignmentSubmission();
        submission.setStudentId(studentId);
        submission.setFileUrl(fileUrl);

        return ResponseEntity.ok(assignmentService.submitAssignment(assignmentId, submission));
    }
}
