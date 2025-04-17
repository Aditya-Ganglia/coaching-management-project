package com.coaching.institute.model;

import lombok.Data;

@Data
public class ScheduleSlot {
    private String day;
    private String time;

    public ScheduleSlot() {}

    public ScheduleSlot(String day, String time) {
        this.day = day;
        this.time = time;
    }

    // Getters and Setters
}
