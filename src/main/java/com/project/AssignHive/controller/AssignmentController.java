package com.project.AssignHive.controller;

import com.project.AssignHive.entity.Assignment;
import com.project.AssignHive.services.AssignmentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin("*")
public class AssignmentController {

    @Autowired
    private AssignmentServices assignmentService;

    // Create an Assignment
    @PostMapping("/{subjectName}/{createdBy}")
    public ResponseEntity<Assignment> createAssignment(
            @PathVariable String subjectName,
            @PathVariable String createdBy,
            @RequestBody Assignment assignment) {
        Assignment createdAssignment = assignmentService.createAssignment(subjectName,createdBy,assignment);
        return ResponseEntity.ok(createdAssignment);
    }

    // Get All Assignments for a Subject by User
    @GetMapping("/{subjectName}/{createdBy}")
    public ResponseEntity<List<Assignment>> getAssignmentsBySubject(
            @PathVariable String subjectName,
            @PathVariable String createdBy) {
        List<Assignment> assignments = assignmentService.getAssignmentsBySubject(subjectName, createdBy);
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/{createdBy}")
    public ResponseEntity<List<Assignment>> getAssignmentsByUsername(
            @PathVariable String createdBy) {
        List<Assignment> assignments = assignmentService.getAssignmentsByUser(createdBy);
        return ResponseEntity.ok(assignments);
    }

    // Update an Assignment
    @PutMapping("/{subjectName}/{createdBy}/{title}")
    public ResponseEntity<Assignment> updateAssignment(
            @PathVariable String subjectName,
            @PathVariable String createdBy,
            @PathVariable String title,
            @RequestBody Assignment updatedAssignment) {
        Assignment assignment = assignmentService.updateAssignment(subjectName, createdBy, title, updatedAssignment);
        return ResponseEntity.ok(assignment);
    }

    // Delete an Assignment
    @DeleteMapping("/{subjectName}/{createdBy}/{title}")
    public ResponseEntity<String> deleteAssignment(
            @PathVariable String subjectName,
            @PathVariable String createdBy,
            @PathVariable String title) {
        assignmentService.deleteAssignment(subjectName, createdBy, title);
        return ResponseEntity.ok("Assignment Deleted Successfully");
    }
}