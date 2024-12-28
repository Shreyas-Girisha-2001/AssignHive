package com.project.AssignHive.serviceImpl;

import com.project.AssignHive.entity.Subject;
import com.project.AssignHive.entity.User;
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

    @Override
    public Subject createSubjectForUser(String username, Subject subject) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        // Initialize the subjects list if null
        if (user.getSubjects() == null) {
            user.setSubjects(new ArrayList<>());
        }

        // Check if the subject already exists for this user
        if (user.getSubjects().contains(subject.getName())) {
            throw new RuntimeException("Subject with name '" + subject.getName() + "' already exists for user: " + username);
        }

        // Check if the subject already exists in the Subject collection for the same user
        Optional<Subject> existingSubject = subjectRepository.findByNameAndCreatedBy(subject.getName(), username);
        if (existingSubject.isPresent()) {
            throw new RuntimeException("Subject with name '" + subject.getName() + "' already exists for user: " + username);
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
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        return subjectRepository.findAllByNameIn(user.getSubjects());
    }

    @Override
    public Subject updateSubjectForUser(String username, String subjectName, Subject updatedSubject) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        if (!user.getSubjects().contains(subjectName)) {
            throw new RuntimeException("Subject with name '" + subjectName + "' not found for user: " + username);
        }

        Subject existingSubject = subjectRepository.findByNameAndCreatedBy(subjectName, username)
                .orElseThrow(() -> new RuntimeException("Subject not found with name: " + subjectName + " for user: " + username));

        existingSubject.setName(updatedSubject.getName());
        existingSubject.setDescription(updatedSubject.getDescription());

        user.getSubjects().remove(subjectName);
        user.getSubjects().add(updatedSubject.getName());
        userRepository.save(user);
        return subjectRepository.save(existingSubject);
    }

    @Override
    public void deleteSubjectForUser(String username, String subjectName) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        if (!user.getSubjects().contains(subjectName)) {
            throw new RuntimeException("Subject with name '" + subjectName + "' not found for user: " + username);
        }

        // Find and delete the subject
        Subject subjectToDelete = subjectRepository.findByNameAndCreatedBy(subjectName, username)
                .orElseThrow(() -> new RuntimeException("Subject not found with name: " + subjectName + " for user: " + username));
        subjectRepository.delete(subjectToDelete);

        // Remove the subject from the user's subjects list
        user.getSubjects().remove(subjectName);
        userRepository.save(user);
    }
}