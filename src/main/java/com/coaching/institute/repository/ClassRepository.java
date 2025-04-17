package com.coaching.institute.repository;

import com.coaching.institute.model.ClassInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ClassRepository extends MongoRepository<ClassInfo, String> {
    List<ClassInfo> findByTeacherId(String teacherId);
    List<ClassInfo> findByStudentIdsContaining(String studentId);
}
