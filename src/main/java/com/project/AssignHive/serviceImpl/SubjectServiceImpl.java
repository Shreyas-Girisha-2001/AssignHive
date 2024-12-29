package com.project.AssignHive.serviceImpl;

import com.project.AssignHive.entity.Assignment;
import com.project.AssignHive.entity.Subject;
import com.project.AssignHive.entity.User;
import com.project.AssignHive.exception.ResourceNotFoundException;
import com.project.AssignHive.repository.AssignmentRepository;
import com.project.AssignHive.repository.SubjectRepository;
import com.project.AssignHive.repository.UserRepository;
import com.project.AssignHive.services.SubjectServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectServices {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Override
    public Subject createSubjectForUser(String username, Subject subject) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        // Initialize the subjects list if null
        if (user.getSubjects() == null) {
            user.setSubjects(new ArrayList<>());
        }

        // Check if the subject already exists for this user
        if (user.getSubjects().contains(subject.getName())) {
            throw new ResourceNotFoundException("Subject with name '" + subject.getName() + "' already exists for user: " + username);
        }

        // Check if the subject already exists in the Subject collection for the same user
        Optional<Subject> existingSubject = subjectRepository.findByNameAndCreatedBy(subject.getName(), username);
        if (existingSubject.isPresent()) {
            throw new ResourceNotFoundException("Subject with name '" + subject.getName() + "' already exists for user: " + username);
        }

        // Set the createdBy field and save the subject to the Subject collection
        subject.setCreatedBy(username);
        Subject savedSubject = subjectRepository.save(subject);

        // Add the subject name to the user's subject list and save the user
        user.getSubjects().add(savedSubject.getName());
        userRepository.save(user);

        return savedSubject;
    }

    @Override
    public List<Subject> getSubjectsForUser(String username) {
        // Validate the user exists
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        // Fetch subjects for the user
        return subjectRepository.findAllByNameInAndCreatedBy(user.getSubjects(), username);
    }

    @Override
    public Subject updateSubjectForUser(String username, String subjectName, Subject updatedSubject) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        if (!user.getSubjects().contains(subjectName)) {
            throw new ResourceNotFoundException("Subject with name '" + subjectName + "' not found for user: " + username);
        }

        Subject existingSubject = subjectRepository.findByNameAndCreatedBy(subjectName, username)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with name: " + subjectName + " for user: " + username));

        existingSubject.setName(updatedSubject.getName());
        existingSubject.setDescription(updatedSubject.getDescription());

        user.getSubjects().remove(subjectName);
        user.getSubjects().add(updatedSubject.getName());
        userRepository.save(user);
        return subjectRepository.save(existingSubject);
    }

    @Override
    public void deleteSubject(String subjectName, String createdBy) {
        // Validate the subject exists for the specific user
        Subject subject = subjectRepository.findByNameAndCreatedBy(subjectName, createdBy)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subject not found with name: " + subjectName + " for user: " + createdBy));

        // Delete all assignments linked to the subject for the specific user
        assignmentRepository.deleteBySubjectNameAndCreatedBy(subjectName, createdBy);

        // Delete the subject
        subjectRepository.delete(subject);

        // Update the user's subjects array
        Optional<User> userOptional = userRepository.findByUsername(createdBy);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.getSubjects().remove(subjectName);
            userRepository.save(user);
        }
    }
}