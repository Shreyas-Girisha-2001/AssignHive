package com.project.AssignHive.repository;

import com.project.AssignHive.entity.Assignment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AssignmentRepository extends MongoRepository<Assignment, String> {

    // Find all assignments for a specific subject and user
    @Query("{ 'subjectName': ?0, 'createdBy': ?1 }")
    List<Assignment> findBySubjectNameAndCreatedBy(String subjectName, String createdBy);

    // Find a specific assignment by name, subject, and user
    @Query("{ 'title': ?0, 'subjectName': ?1, 'createdBy': ?2 }")
    Optional<Assignment> findByNameAndSubjectNameAndCreatedBy(String assignmentName, String subjectName, String createdBy);

    // Delete assignments by subject and user
    @Query(value = "{ 'subjectName': ?0, 'createdBy': ?1 }", delete = true)
    void deleteBySubjectNameAndCreatedBy(String subjectName, String createdBy);

    // Find all assignments for a user
    @Query("{ 'createdBy': ?0 }")
    List<Assignment> findByCreatedBy(String createdBy);

    // Check if an assignment exists for the given parameters
    @Query("{ 'name': ?0, 'subjectName': ?1, 'createdBy': ?2 }")
    boolean existsByNameAndSubjectNameAndCreatedBy(String assignmentName, String subjectName, String createdBy);

    @Query("{ 'title': ?0, 'subjectName': ?1, 'createdBy': ?2 }")
    Optional<Assignment> findByTitleAndSubjectNameAndCreatedBy(String title, String subjectName, String createdBy);
}