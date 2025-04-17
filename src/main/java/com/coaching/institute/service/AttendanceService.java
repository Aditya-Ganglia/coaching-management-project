package com.coaching.institute.service;

import com.coaching.institute.model.Attendance;
import com.coaching.institute.model.AttendanceRecord;
import com.coaching.institute.repository.AttendanceRepository;
import com.coaching.institute.repository.ClassRepository;
import com.coaching.institute.dto.StudentAttendanceReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ClassRepository classRepository;

    // ✅ Mark attendance
    public Attendance markAttendance(Attendance attendance) {
        if (!classRepository.existsById(attendance.getClassId())) {
            throw new RuntimeException("Class ID not found: " + attendance.getClassId());
        }

        Optional<Attendance> existing = attendanceRepository.findByClassIdAndDate(
                attendance.getClassId(), attendance.getDate());

        if (existing.isPresent()) {
            throw new RuntimeException("Attendance already marked for this class on this date.");
        }

        return attendanceRepository.save(attendance);
    }

    // ✅ Get all attendance records for a class
    public List<Attendance> getByClass(String classId) {
        return attendanceRepository.findByClassId(classId);
    }

    // ✅ Get attendance record by class + date
    public Optional<Attendance> getByClassAndDate(String classId, LocalDate date) {
        return attendanceRepository.findByClassIdAndDate(classId, date);
    }

    // ✅ NEW: Generate attendance % report for a student in a class
    public StudentAttendanceReport getStudentAttendanceReport(String studentId, String classId) {
        List<Attendance> classAttendances = attendanceRepository.findByClassId(classId);

        int totalClasses = 0;
        int presentCount = 0;

        for (Attendance attendance : classAttendances) {
            totalClasses++;
            Optional<AttendanceRecord> match = attendance.getAttendance()
                    .stream()
                    .filter(a -> a.getStudentId().equals(studentId))
                    .findFirst();

            if (match.isPresent() && match.get().getStatus().equalsIgnoreCase("Present")) {
                presentCount++;
            }
        }

        int absentCount = totalClasses - presentCount;

        return new StudentAttendanceReport(studentId, classId, totalClasses, presentCount, absentCount);
    }
}
