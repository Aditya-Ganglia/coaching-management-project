package com.coaching.institute.repository;

import com.coaching.institute.model.Assignment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AssignmentRepository extends MongoRepository<Assignment, String> {
    List<Assignment> findByClassId(String classId);
    List<Assignment> findByUploadedBy(String teacherId);
}
