package com.project.AssignHive.repository;

import com.project.AssignHive.entity.Subtask;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SubTaskRepository extends MongoRepository<Subtask, String> {

    // Find subtasks by assignment name, subject name, and creator
    @Query("{ 'assignmentName': ?0, 'subjectName': ?1, 'createdBy': ?2 }")
    List<Subtask> findByAssignmentNameAndSubjectNameAndCreatedBy(String assignmentName, String subjectName, String createdBy);
    // Delete all subtasks for a given assignment, subject, and user
    @Query(value = "{ 'assignmentName': ?0, 'subjectName': ?1, 'createdBy': ?2 }", delete = true)
    void deleteByAssignmentNameAndSubjectNameAndCreatedBy(String title, String subjectName, String createdBy);
}