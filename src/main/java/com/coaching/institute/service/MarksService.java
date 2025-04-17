package com.coaching.institute.service;

import com.coaching.institute.dto.SubjectResultReport;
import com.coaching.institute.model.Marks;
import com.coaching.institute.repository.MarksRepository;
import com.coaching.institute.repository.ClassRepository;
import com.coaching.institute.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            throw new RuntimeException("Class ID not found");
        }
        if (!userRepository.existsById(marks.getStudentId())) {
            throw new RuntimeException("Student ID not found");
        }

        String teacherId = userRepository.findByEmail(teacherEmail)
                .orElseThrow(() -> new RuntimeException("Teacher not found"))
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

    // ✅ Optional: Get marks for a student for a subject & exam (like Mid Term)
    public List<Marks> getByStudentSubjectAndExam(String studentId, String subject, String examTitle) {
        return marksRepository.findByStudentIdAndSubjectAndExamTitle(studentId, subject, examTitle);
    }
    public List<SubjectResultReport> getResultSummaryByStudent(String studentId) {
        List<Marks> allMarks = marksRepository.findByStudentId(studentId);

        Map<String, SubjectResultReport> subjectMap = new HashMap<>();

        for (Marks mark : allMarks) {
            String subject = mark.getSubject();
            String exam = mark.getExamTitle();

            subjectMap.putIfAbsent(subject, new SubjectResultReport(subject));
            subjectMap.get(subject).addMarks(exam, mark.getMarksObtained(), mark.getMaxMarks());
        }

        return new ArrayList<>(subjectMap.values());
    }

}
