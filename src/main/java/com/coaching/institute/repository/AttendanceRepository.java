package com.coaching.institute.repository;

import com.coaching.institute.model.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends MongoRepository<Attendance, String> {
    Optional<Attendance> findByClassIdAndDate(String classId, LocalDate date);
    List<Attendance> findByClassId(String classId);
    List<Attendance> findByAttendanceStudentId(String studentId); // Custom mapping
}
