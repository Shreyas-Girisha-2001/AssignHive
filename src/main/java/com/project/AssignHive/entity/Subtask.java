package com.project.AssignHive.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "subtasks")
public class Subtask {

    @Id
    private String id; // Unique identifier for the subtask

    private String assignmentId; // ID of the associated assignment
    private String title; // Title or name of the subtask
    private String description; // Optional description of the subtask
    private LocalDate dueDate; // Deadline for the subtask
    private boolean completed; // Status of the subtask (completed or not)

    // Default constructor
    public Subtask() {
    }

    // Parameterized constructor
    public Subtask(String id, String assignmentId, String title, String description, LocalDate dueDate, boolean completed) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
