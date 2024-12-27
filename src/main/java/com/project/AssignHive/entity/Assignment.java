package com.project.AssignHive.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "assignments")
public class Assignment {

    @Id
    private String id;
    private String subjectId; // Reference to the subject
    private String title;
    private String description;
    private LocalDate dueDate;

    // List of subtasks for this assignment
    private List<String> subtaskIds = new ArrayList<>();

    public Assignment() {
    }

    public Assignment(String id, String subjectId, String title, String description, LocalDate dueDate, List<String> subtaskIds) {
        this.id = id;
        this.subjectId = subjectId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.subtaskIds = subtaskIds;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
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

    public List<String> getSubtaskIds() {
        return subtaskIds;
    }

    public void setSubtaskIds(List<String> subtaskIds) {
        this.subtaskIds = subtaskIds;
    }
}
