package com.project.AssignHive.serviceImpl;

import com.project.AssignHive.entity.Assignment;
import com.project.AssignHive.exception.ResourceNotFoundException;
import com.project.AssignHive.repository.AssignmentRepository;
import com.project.AssignHive.repository.SubjectRepository;
import com.project.AssignHive.services.AssignmentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentServices {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public Assignment createAssignment(String subjectName, String createdBy, Assignment assignment) {
        // Validate subject existence for the given user
        subjectRepository.findByNameAndCreatedBy(subjectName, createdBy)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subject not found with name: " + subjectName + " for user: " + createdBy));

        // Check if assignment already exists
        boolean exists = assignmentRepository.findBySubjectNameAndCreatedBy(subjectName, createdBy).stream()
                .anyMatch(a -> a.getSubjectName().equals(assignment.getSubjectName()));

        if (exists) {
            throw new IllegalArgumentException("Assignment with the same name already exists for this subject and user.");
        }

        // Set additional fields
        assignment.setSubjectName(subjectName);
        assignment.setCreatedBy(createdBy);

        return assignmentRepository.save(assignment);
    }

    @Override
    public List<Assignment> getAssignmentsBySubject(String subjectName, String createdBy) {
        // Validate the subject exists
        subjectRepository.findByNameAndCreatedBy(subjectName,createdBy)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with name: " + subjectName));

        // Fetch assignments
        return assignmentRepository.findBySubjectNameAndCreatedBy(subjectName, createdBy);
    }

    @Override
    public Assignment updateAssignment(String subjectName, String createdBy, String title, Assignment updatedAssignment) {
        // Fetch the existing assignment
        Assignment existing = assignmentRepository.findByTitleAndSubjectNameAndCreatedBy(title, subjectName, createdBy)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with title: " + title + " for user: " + createdBy + " under subject: " + subjectName));

        // Update fields
        existing.setTitle(updatedAssignment.getTitle());
        existing.setDescription(updatedAssignment.getDescription());
        existing.setDueDate(updatedAssignment.getDueDate());
        return assignmentRepository.save(existing);
    }

    @Override
    public void deleteAssignment(String subjectName, String assignmentName, String createdBy) {
        // Find the assignment to delete
        Assignment assignment = assignmentRepository.findBySubjectNameAndCreatedBy(subjectName, createdBy).stream()
                .filter(a -> a.getSubjectName().equals(assignmentName))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Assignment not found with name: " + assignmentName + " for user: " + createdBy
                ));

        assignmentRepository.delete(assignment);
    }

    @Override
    public void deleteAssignmentsBySubject(String subjectName, String createdBy) {
        // Delete assignments associated with the subject and user
        assignmentRepository.deleteBySubjectNameAndCreatedBy(subjectName, createdBy);
    }
}