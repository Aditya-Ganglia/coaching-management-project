package com.coaching.institute.controller;

import com.coaching.institute.model.Attendance;
import com.coaching.institute.service.AttendanceService;
import com.coaching.institute.dto.StudentAttendanceReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // ✅ Mark attendance
    @PostMapping("/mark")
    public ResponseEntity<Attendance> markAttendance(@RequestBody Attendance attendance) {
        return ResponseEntity.ok(attendanceService.markAttendance(attendance));
    }

    // ✅ Get all attendance records for a class
    @GetMapping("/class/{classId}")
    public ResponseEntity<List<Attendance>> getByClass(@PathVariable String classId) {
        return ResponseEntity.ok(attendanceService.getByClass(classId));
    }

    // ✅ Get attendance by class + date
    @GetMapping("/class/{classId}/date/{date}")
    public ResponseEntity<Attendance> getByClassAndDate(
            @PathVariable String classId,
            @PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return attendanceService.getByClassAndDate(classId, localDate)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ NEW: Get student attendance percentage report
    @GetMapping("/report/class/{classId}/student/{studentId}")
    public ResponseEntity<StudentAttendanceReport> getAttendanceReport(
            @PathVariable String classId,
            @PathVariable String studentId
    ) {
        return ResponseEntity.ok(attendanceService.getStudentAttendanceReport(studentId, classId));
    }
}
