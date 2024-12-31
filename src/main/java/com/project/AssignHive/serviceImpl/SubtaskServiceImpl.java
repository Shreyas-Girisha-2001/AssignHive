package com.project.AssignHive.serviceImpl;

import com.project.AssignHive.entity.Subtask;
import com.project.AssignHive.exception.ResourceNotFoundException;
import com.project.AssignHive.repository.AssignmentRepository;
import com.project.AssignHive.repository.SubTaskRepository;
import com.project.AssignHive.repository.SubjectRepository;
import com.project.AssignHive.services.SubTaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubtaskServiceImpl implements SubTaskServices {

    @Autowired
    private SubTaskRepository subtaskRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public Subtask createSubtask(String subjectName, String assignmentName, String createdBy, Subtask subtask) {
        // Validate subject existence for the user
        subjectRepository.findByNameAndCreatedBy(subjectName, createdBy)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subject not found with name: " + subjectName + " for user: " + createdBy));

        // Validate assignment existence for the subject and user
        assignmentRepository.findByNameAndSubjectNameAndCreatedBy(assignmentName, subjectName, createdBy).orElseThrow(() -> new ResourceNotFoundException(
                        "Assignment not found with name: " + assignmentName + " under subject: " + subjectName));

        // Check for duplicate subtask
        boolean exists = subtaskRepository.findByAssignmentNameAndSubjectNameAndCreatedBy(assignmentName, subjectName, createdBy)
                .stream()
                .anyMatch(existingSubtask -> existingSubtask.getTitle().equals(subtask.getTitle()));

        if (exists) {
            throw new ResourceNotFoundException("Subtask with the same title already exists under this assignment.");
        }

        // Set relationship fields
        subtask.setAssignmentName(assignmentName);
        subtask.setSubjectName(subjectName);
        subtask.setCreatedBy(createdBy);

        return subtaskRepository.save(subtask);
    }

    @Override
    public Subtask updateSubtask(String subjectName, String assignmentName, String createdBy, String subtaskTitle, Subtask subtask) {
        Subtask existingSubtask = subtaskRepository.findByAssignmentNameAndSubjectNameAndCreatedBy(assignmentName, subjectName, createdBy)
                .stream()
                .filter(st -> st.getTitle().equals(subtaskTitle))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Subtask not found with title: " + subtaskTitle));

        existingSubtask.setTitle(subtask.getTitle());
        existingSubtask.setDeadline(subtask.getDeadline());
        return subtaskRepository.save(existingSubtask);
    }

    @Override
    public void deleteSubtask(String subjectName, String assignmentName, String createdBy, String subtaskTitle) {
        Subtask subtask = subtaskRepository.findByAssignmentNameAndSubjectNameAndCreatedBy(assignmentName, subjectName, createdBy)
                .stream()
                .filter(st -> st.getTitle().equals(subtaskTitle))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Subtask not found with title: " + subtaskTitle));

        subtaskRepository.delete(subtask);
    }

    @Override
    public List<Subtask> getSubtasks(String subjectName, String assignmentName, String createdBy) {
        return subtaskRepository.findByAssignmentNameAndSubjectNameAndCreatedBy(assignmentName, subjectName, createdBy);
    }
}