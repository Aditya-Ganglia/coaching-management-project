package com.coaching.institute.model;

import lombok.Data;

@Data
public class AttendanceRecord {
    private String studentId;
    private String status; // "Present" or "Absent"

    public AttendanceRecord() {}

    public AttendanceRecord(String studentId, String status) {
        this.studentId = studentId;
        this.status = status;
    }

    // Getters and Setters
}
