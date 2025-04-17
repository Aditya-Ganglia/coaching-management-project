package com.coaching.institute.repository;

import com.coaching.institute.model.Marks;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MarksRepository extends MongoRepository<Marks, String> {

    List<Marks> findByStudentId(String studentId);

    List<Marks> findByClassId(String classId);

    List<Marks> findByClassIdAndStudentId(String classId, String studentId);

    // ✅ NEW: Fetch all marks by subject for a student
    List<Marks> findByStudentIdAndSubject(String studentId, String subject);

    // ✅ (Optional) Fetch all marks by subject + exam type for detailed filtering
    List<Marks> findByStudentIdAndSubjectAndExamTitle(String studentId, String subject, String examTitle);
}
