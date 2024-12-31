package com.project.AssignHive.services;


import com.project.AssignHive.entity.Subtask;

import java.util.List;

public interface SubTaskServices {
    Subtask createSubtask(String subjectName, String assignmentName, String createdBy, Subtask subtask);
    Subtask updateSubtask(String subjectName, String assignmentName, String createdBy, String subtaskTitle, Subtask subtask);
    void deleteSubtask(String subjectName, String assignmentName, String createdBy, String subtaskTitle);
    List<Subtask> getSubtasks(String subjectName, String assignmentName, String createdBy);
}
