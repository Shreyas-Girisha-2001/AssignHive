package com.project.AssignHive.repository;

import com.project.AssignHive.entity.Subject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends MongoRepository<Subject, String> {
    Optional<Subject> findByNameAndCreatedBy(String name, String createdBy);
    void deleteByName(String name);
    List<Subject> findAllByNameIn(List<String> names);
    Optional<Object> findByName(String subjectName);
    List<Subject> findAllByNameInAndCreatedBy(List<String> names, String createdBy);
}
