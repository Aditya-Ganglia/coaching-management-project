package com.coaching.institute.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssignmentSubmission {
    private String studentId;
    private String fileUrl; // or answer text
    private LocalDateTime submittedAt;

    public AssignmentSubmission() {}

    public AssignmentSubmission(String studentId, String fileUrl, LocalDateTime submittedAt) {
        this.studentId = studentId;
        this.fileUrl = fileUrl;
        this.submittedAt = submittedAt;
    }

    // Getters and Setters
}
