package com.coaching.institute.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "attendance")
public class Attendance {

    @Id
    private String id;

    private String classId;
    private LocalDate date;

    private List<AttendanceRecord> attendance;

    public Attendance() {}

    public Attendance(String classId, LocalDate date, List<AttendanceRecord> attendance) {
        this.classId = classId;
        this.date = date;
        this.attendance = attendance;
    }

    // Getters and Setters
}
