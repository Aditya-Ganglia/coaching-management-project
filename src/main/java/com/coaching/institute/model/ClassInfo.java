package com.coaching.institute.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "classes")
public class ClassInfo {
    @Id
    private String id;

    private String subject;
    private String batchName;
    private String teacherId;
    private List<String> studentIds;

    private List<ScheduleSlot> schedule; // e.g., Mon 10:00 AM

    // Constructors
    public ClassInfo() {}

    public ClassInfo(String subject, String batchName, String teacherId, List<String> studentIds, List<ScheduleSlot> schedule) {
        this.subject = subject;
        this.batchName = batchName;
        this.teacherId = teacherId;
        this.studentIds = studentIds;
        this.schedule = schedule;
    }

    // Getters and Setters
    // (You can generate or use Lombok)
}
