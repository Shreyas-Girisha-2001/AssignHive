package com.project.AssignHive.serviceImpl;

import com.project.AssignHive.entity.Assignment;
import com.project.AssignHive.exception.ResourceNotFoundException;
import com.project.AssignHive.repository.AssignmentRepository;
import com.project.AssignHive.repository.SubjectRepository;
import com.project.AssignHive.services.AssignmentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
                .orElseThrow(() -> new DataIntegrityViolationException(
                        "Subject not found with name: " + subjectName + " for user: " + createdBy));

        // Check if an assignment with the same name already exists for the user and subject
        boolean exists = assignmentRepository.findBySubjectNameAndCreatedBy(subjectName, createdBy).stream()
                .anyMatch(a -> a.getTitle().equals(assignment.getTitle())); // Check assignment name instead

        if (exists) {
            throw new DataIntegrityViolationException("Assignment with the same name already exists for this subject and user.");
        }

        // Set additional fields
        assignment.setSubjectName(subjectName);
        assignment.setCreatedBy(createdBy);
        assignment.setCompleted(false);

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
    public List<Assignment> getAssignmentsByUser(String createdBy){
        List<Assignment> assignmentList =assignmentRepository.findByCreatedBy(createdBy);
        if(assignmentList.isEmpty()){
            throw new ResourceNotFoundException("No Assignments found for user : "+createdBy);
        }
        return assignmentList;
    }

    @Override
    public void setCompleted(String subjectName, String createdBy, String title) {
        // Fetch the existing assignment
        Assignment existing = assignmentRepository.findByTitleAndSubjectNameAndCreatedBy(title, subjectName, createdBy)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with title: " + title + " for user: " + createdBy + " under subject: " + subjectName));

        existing.setCompleted(true);
        assignmentRepository.save(existing);

    }

    @Override
    public void inCompleted(String subjectName, String createdBy, String title) {
        // Fetch the existing assignment
        Assignment existing = assignmentRepository.findByTitleAndSubjectNameAndCreatedBy(title, subjectName, createdBy)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with title: " + title + " for user: " + createdBy + " under subject: " + subjectName));

        existing.setCompleted(false);
        assignmentRepository.save(existing);
    }

    @Override
    public Assignment updateAssignment(String subjectName, String createdBy, String title, Assignment updatedAssignment) {
        // Fetch the existing assignment
        Assignment existing = assignmentRepository.findByTitleAndSubjectNameAndCreatedBy(title, subjectName, createdBy)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with title: " + title + " for user: " + createdBy + " under subject: " + subjectName));

        // Update fields
        existing.setSubjectName(updatedAssignment.getSubjectName());
        existing.setTitle(updatedAssignment.getTitle());
        existing.setDescription(updatedAssignment.getDescription());
        existing.setDueDate(updatedAssignment.getDueDate());
        existing.setCompleted(false);
        return assignmentRepository.save(existing);
    }

    @Override
    public void deleteAssignment(String subjectName, String assignmentName, String createdBy) {
        // Find the assignment to delete
        Assignment assignment = assignmentRepository.findBySubjectNameAndCreatedBy(subjectName, createdBy).stream()
                .filter(a -> a.getTitle().equals(assignmentName))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Assignment not found with name: " + assignmentName + " for user: " + createdBy
                ));
        assignmentRepository.delete(assignment);
    }
}