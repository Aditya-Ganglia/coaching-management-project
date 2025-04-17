package com.coaching.institute.dto;

import lombok.Data;

@Data
public class StudentAttendanceReport {
    private String studentId;
    private String classId;
    private int totalClasses;
    private int present;
    private int absent;
    private double attendancePercentage;

    public StudentAttendanceReport() {}

    public StudentAttendanceReport(String studentId, String classId, int totalClasses, int present, int absent) {
        this.studentId = studentId;
        this.classId = classId;
        this.totalClasses = totalClasses;
        this.present = present;
        this.absent = absent;
        this.attendancePercentage = totalClasses > 0 ? ((double) present / totalClasses) * 100 : 0.0;
    }

    // Getters and Setters
}
