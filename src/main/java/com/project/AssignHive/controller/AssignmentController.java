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
    private final AssignmentServices assignmentService;

    public AssignmentController(AssignmentServices assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping("/{subjectId}")
    public ResponseEntity<Assignment> addAssignment(@PathVariable String subjectId, @RequestBody Assignment assignment) {
        return ResponseEntity.ok(assignmentService.addAssignment(subjectId, assignment));
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<List<Assignment>> getAssignmentsBySubjectId(@PathVariable String subjectId) {
        return ResponseEntity.ok(assignmentService.getAssignmentsBySubjectId(subjectId));
    }
}
