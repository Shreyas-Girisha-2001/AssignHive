package com.project.AssignHive.controller;

import com.project.AssignHive.entity.Subject;
import com.project.AssignHive.services.SubjectServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    private SubjectServices subjectService;

    // Create Subject for a User
    @PostMapping("/{username}")
    public ResponseEntity<Subject> createSubjectForUser(@PathVariable String username, @RequestBody Subject subject) {
        Subject createdSubject = subjectService.createSubjectForUser(username, subject);
        return ResponseEntity.ok(createdSubject);
    }

    // Get All Subjects for a User
    @GetMapping("/{username}")
    public ResponseEntity<List<Subject>> getSubjectsForUser(@PathVariable String username) {
        List<Subject> subjects = subjectService.getSubjectsForUser(username);
        return ResponseEntity.ok(subjects);
    }

    // Update a Subject for a User
    @PutMapping("/{username}/{subjectName}")
    public ResponseEntity<Subject> updateSubjectForUser(@PathVariable String username, @PathVariable String subjectName, @RequestBody Subject updatedSubject) {
        Subject subject = subjectService.updateSubjectForUser(username, subjectName, updatedSubject);
        return ResponseEntity.ok(subject);
    }

    // Delete a Subject for a User
    @DeleteMapping("/{username}/{subjectName}")
    public ResponseEntity<Void> deleteSubject(
            @PathVariable String username,
            @PathVariable String subjectName) {
        subjectService.deleteSubject(subjectName, username);
        return ResponseEntity.noContent().build();
    }
}