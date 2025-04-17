package com.coaching.institute.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class SubjectResultReport {

    private String subject;
    private Map<String, Integer> obtainedMarksPerExam = new HashMap<>();
    private Map<String, Integer> maxMarksPerExam = new HashMap<>();

    private int totalObtained;
    private int totalMax;
    private double percentage;

    public SubjectResultReport(String subject) {
        this.subject = subject;
    }

    public void addMarks(String examTitle, int obtained, int max) {
        obtainedMarksPerExam.put(examTitle, obtainedMarksPerExam.getOrDefault(examTitle, 0) + obtained);
        maxMarksPerExam.put(examTitle, maxMarksPerExam.getOrDefault(examTitle, 0) + max);

        totalObtained += obtained;
        totalMax += max;
        percentage = totalMax > 0 ? ((double) totalObtained / totalMax) * 100 : 0.0;
    }

    // Getters & Setters
}
