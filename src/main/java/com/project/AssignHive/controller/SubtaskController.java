package com.project.AssignHive.controller;

import com.project.AssignHive.entity.Subtask;
import com.project.AssignHive.services.SubTaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subtasks")
@CrossOrigin("*")
public class SubtaskController {
    @Autowired
    private final SubTaskServices subTaskServices;

    public SubtaskController(SubTaskServices subTaskServices) {
        this.subTaskServices = subTaskServices;
    }

    @PostMapping("/{assignmentId}")
    public ResponseEntity<Subtask> addSubtask(@PathVariable String assignmentId, @RequestBody Subtask subtask) {
        return ResponseEntity.ok(subTaskServices.addSubtask(assignmentId, subtask));
    }

    @GetMapping("/{assignmentId}")
    public ResponseEntity<List<Subtask>> getSubtasksByAssignmentId(@PathVariable String assignmentId) {
        return ResponseEntity.ok(subTaskServices.getSubtasksByAssignmentId(assignmentId));
    }

    @DeleteMapping("/{subtaskId}")
    public ResponseEntity<Void> deleteSubtask(@PathVariable String subtaskId) {
        subTaskServices.deleteSubtask(subtaskId);
        return ResponseEntity.noContent().build();
    }
}
