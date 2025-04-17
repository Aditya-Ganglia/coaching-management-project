package com.coaching.institute.dto;

import lombok.Data;

@Data
public class SubjectResultSummary {
    private String subject;
    private int totalObtained;
    private int totalMax;
    private double percentage;

    public SubjectResultSummary() {}

    public SubjectResultSummary(String subject, int totalObtained, int totalMax) {
        this.subject = subject;
        this.totalObtained = totalObtained;
        this.totalMax = totalMax;
        this.percentage = totalMax > 0 ? (totalObtained * 100.0) / totalMax : 0.0;
    }

    // Getters and Setters
}
