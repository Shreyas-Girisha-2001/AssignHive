package com.project.AssignHive.controller;

import com.project.AssignHive.entity.Subtask;
import com.project.AssignHive.services.SubTaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subtasks")
public class SubtaskController {

    @Autowired
    private SubTaskServices subtaskService;

    @PostMapping("/{subjectName}/{assignmentName}/{createdBy}")
    public ResponseEntity<Subtask> createSubtask(
            @PathVariable String subjectName,
            @PathVariable String assignmentName,
            @PathVariable String createdBy,
            @RequestBody Subtask subtask) {
        return ResponseEntity.ok(subtaskService.createSubtask(subjectName, assignmentName, createdBy, subtask));
    }

    @PutMapping("/{subjectName}/{assignmentName}/{createdBy}/{subtaskTitle}")
    public ResponseEntity<Subtask> updateSubtask(
            @PathVariable String subjectName,
            @PathVariable String assignmentName,
            @PathVariable String createdBy,
            @PathVariable String subtaskTitle,
            @RequestBody Subtask subtask) {
        return ResponseEntity.ok(subtaskService.updateSubtask(subjectName, assignmentName, createdBy, subtaskTitle, subtask));
    }

    @DeleteMapping("/{subjectName}/{assignmentName}/{createdBy}/{subtaskTitle}")
    public ResponseEntity<String> deleteSubtask(
            @PathVariable String subjectName,
            @PathVariable String assignmentName,
            @PathVariable String createdBy,
            @PathVariable String subtaskTitle) {
        subtaskService.deleteSubtask(subjectName, assignmentName, createdBy, subtaskTitle);
        return ResponseEntity.ok("Subtask deleted successfully");
    }

    @GetMapping("/{subjectName}/{assignmentName}/{createdBy}")
    public ResponseEntity<List<Subtask>> getSubtasks(
            @PathVariable String subjectName,
            @PathVariable String assignmentName,
            @PathVariable String createdBy) {
        return ResponseEntity.ok(subtaskService.getSubtasks(subjectName, assignmentName, createdBy));
    }
}