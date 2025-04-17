package com.coaching.institute.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "marks")
public class Marks {

    @Id
    private String id;

    private String classId;
    private String studentId;

    private String subject;      // NEW: e.g., "Math", "Science"
    private String examTitle;    // NEW: e.g., "Mid Term", "Final Term"

    private int marksObtained;
    private int maxMarks;

    private String addedBy;      // teacherId
    private LocalDate date;

    public Marks() {}

    public Marks(String classId, String studentId, String subject, String examTitle,
                 int marksObtained, int maxMarks, String addedBy, LocalDate date) {
        this.classId = classId;
        this.studentId = studentId;
        this.subject = subject;
        this.examTitle = examTitle;
        this.marksObtained = marksObtained;
        this.maxMarks = maxMarks;
        this.addedBy = addedBy;
        this.date = date;
    }

    // Getters and Setters
}
