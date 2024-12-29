package com.project.AssignHive.repository;

import com.project.AssignHive.entity.Assignment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AssignmentRepository extends MongoRepository<Assignment, String> {
    Optional<Assignment> findByTitleAndSubjectNameAndCreatedBy(String title, String subjectName, String createdBy);
    List<Assignment> findBySubjectNameAndCreatedBy(String subjectName, String createdBy);
    void deleteByTitleAndSubjectNameAndCreatedBy(String title, String subjectName, String createdBy);
    @Query(value = "{ 'subjectName': ?0, 'createdBy': ?1 }", delete = true)
    void deleteBySubjectNameAndCreatedBy(String subjectName, String createdBy);
}