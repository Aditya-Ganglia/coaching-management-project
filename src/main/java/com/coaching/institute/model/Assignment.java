package com.coaching.institute.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "assignments")
public class Assignment {

    @Id
    private String id;

    private String title;
    private String description;
    private LocalDate deadline;

    private String classId;
    private String uploadedBy; // teacherId

    private List<AssignmentSubmission> submissions;

    public Assignment() {}

    public Assignment(String title, String description, LocalDate deadline,
                      String classId, String uploadedBy, List<AssignmentSubmission> submissions) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.classId = classId;
        this.uploadedBy = uploadedBy;
        this.submissions = submissions;
    }

    // Getters and Setters
}
