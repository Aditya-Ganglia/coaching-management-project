package com.coaching.institute.service;

import com.coaching.institute.dto.SubjectResultReport;
import com.coaching.institute.exception.ResourceNotFoundException;
import com.coaching.institute.exception.UnauthorizedActionException;
import com.coaching.institute.model.Marks;
import com.coaching.institute.repository.MarksRepository;
import com.coaching.institute.repository.ClassRepository;
import com.coaching.institute.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coaching.institute.dto.SubjectResultSummary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MarksService {

    @Autowired
    private MarksRepository marksRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassRepository classRepository;

    // ✅ Add marks with validation
    public Marks addMarks(Marks marks, String teacherEmail) {
        if (!classRepository.existsById(marks.getClassId())) {
            throw new ResourceNotFoundException("Class ID not found");
        }
        if (!userRepository.existsById(marks.getStudentId())) {
            throw new ResourceNotFoundException("Student ID not found");
        }

        String teacherId = userRepository.findByEmail(teacherEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"))
                .getId();

        marks.setAddedBy(teacherId);
        return marksRepository.save(marks);
    }


    // ✅ All marks by student
    public List<Marks> getMarksByStudent(String studentId) {
        return marksRepository.findByStudentId(studentId);
    }

    // ✅ All marks by class
    public List<Marks> getMarksByClass(String classId) {
        return marksRepository.findByClassId(classId);
    }

    // ✅ Marks of student in a specific class
    public List<Marks> getMarksByClassAndStudent(String classId, String studentId) {
        return marksRepository.findByClassIdAndStudentId(classId, studentId);
    }

    // ✅ All marks by subject for a student
    public List<Marks> getMarksBySubjectAndStudent(String studentId, String subject) {
        return marksRepository.findByStudentIdAndSubject(studentId, subject);
    }

    public List<Marks> getByStudentSubjectAndExam(String studentId, String subject, String examTitle) {
        return marksRepository.findByStudentIdAndSubjectAndExamTitle(studentId, subject, examTitle);
    }
    // ✅ Optional: Get marks for a student for a subject & exam (like Mid Term)
    public List<SubjectResultSummary> getResultSummaryByStudent(String studentId) {
        List<Marks> allMarks = marksRepository.findByStudentId(studentId);

        Map<String, SubjectResultSummary> summaryMap = new HashMap<>();

        for (Marks mark : allMarks) {
            String subject = mark.getSubject();

            summaryMap.putIfAbsent(subject, new SubjectResultSummary(subject, 0, 0));

            SubjectResultSummary summary = summaryMap.get(subject);
            summary.setTotalObtained(summary.getTotalObtained() + mark.getMarksObtained());
            summary.setTotalMax(summary.getTotalMax() + mark.getMaxMarks());
            summary.setPercentage((summary.getTotalMax() > 0) ?
                    (summary.getTotalObtained() * 100.0) / summary.getTotalMax() : 0.0);
        }

        return new ArrayList<>(summaryMap.values());
    }

    // ✅ Update Marks Logic with Role-Based Access
    public Marks updateMarks(String marksId, Marks updatedMarks, String userEmail) {
        Marks existingMarks = marksRepository.findById(marksId)
                .orElseThrow(() -> new ResourceNotFoundException("Marks not found"));

        String userRole = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getRole();

        String userId = userRepository.findByEmail(userEmail).get().getId();

        // Allow Admin OR Teacher (who added the marks)
        if (userRole.equals("admin") || (userRole.equals("teacher") && existingMarks.getAddedBy().equals(userId))) {
            existingMarks.setMarksObtained(updatedMarks.getMarksObtained());
            existingMarks.setMaxMarks(updatedMarks.getMaxMarks());
            existingMarks.setExamTitle(updatedMarks.getExamTitle());
            existingMarks.setSubject(updatedMarks.getSubject());
            existingMarks.setDate(updatedMarks.getDate());
            return marksRepository.save(existingMarks);
        } else {
            throw new UnauthorizedActionException("You are not authorized to perform this action");
        }
    }

    // ✅ Delete Marks Logic with Role-Based Access
    public void deleteMarks(String marksId, String userEmail) {
        Marks existingMarks = marksRepository.findById(marksId)
                .orElseThrow(() -> new ResourceNotFoundException("Marks not found"));

        String userRole = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getRole();

        String userId = userRepository.findByEmail(userEmail).get().getId();

        // Allow Admin OR Teacher (who added the marks)
        if (userRole.equals("admin") || (userRole.equals("teacher") && existingMarks.getAddedBy().equals(userId))) {
            marksRepository.deleteById(marksId);
        } else {
            throw new UnauthorizedActionException("You are not authorized to perform this action");
        }
    }

}
