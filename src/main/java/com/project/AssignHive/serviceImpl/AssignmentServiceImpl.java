package com.project.AssignHive.serviceImpl;

import com.project.AssignHive.entity.Assignment;
import com.project.AssignHive.repository.AssignmentRepository;
import com.project.AssignHive.services.AssignmentServices;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentServices {

    private final AssignmentRepository assignmentRepository;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public Assignment addAssignment(String subjectId, Assignment assignment) {
        // Set the subject ID for the assignment
        assignment.setSubjectId(subjectId);
        // Save the assignment to the repository
        return assignmentRepository.save(assignment);
    }

    @Override
    public List<Assignment> getAssignmentsBySubjectId(String subjectId) {
        // Fetch assignments by subject ID
        return assignmentRepository.findBySubjectId(subjectId);
    }

    @Override
    public void deleteAssignment(String assignmentId) {
        // Delete assignment by ID
        assignmentRepository.deleteById(assignmentId);
    }
}
