package com.coaching.institute.service;

import com.coaching.institute.model.Assignment;
import com.coaching.institute.model.AssignmentSubmission;
import com.coaching.institute.repository.AssignmentRepository;
import com.coaching.institute.repository.ClassRepository;
import com.coaching.institute.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassRepository classRepository;

    public Assignment uploadAssignment(Assignment assignment) {
        // Validate teacher and class
        if (!userRepository.existsById(assignment.getUploadedBy())) {
            throw new RuntimeException("Invalid teacher ID.");
        }
        if (!classRepository.existsById(assignment.getClassId())) {
            throw new RuntimeException("Invalid class ID.");
        }
        return assignmentRepository.save(assignment);
    }

    public Assignment submitAssignment(String assignmentId, AssignmentSubmission submission) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        submission.setSubmittedAt(LocalDateTime.now());

        assignment.getSubmissions().add(submission);
        return assignmentRepository.save(assignment);
    }

    public List<Assignment> getAssignmentsByClass(String classId) {
        return assignmentRepository.findByClassId(classId);
    }

    public List<Assignment> getAssignmentsByTeacher(String teacherId) {
        return assignmentRepository.findByUploadedBy(teacherId);
    }

    public Optional<Assignment> getAssignmentById(String id) {
        return assignmentRepository.findById(id);
    }
}
