package com.project.AssignHive.repository;

import com.project.AssignHive.entity.Subtask;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubTaskRepository extends MongoRepository<Subtask,String> {
    List<Subtask> findByAssignmentId(String assignmentId);
}
