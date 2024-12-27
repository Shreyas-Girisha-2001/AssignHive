package com.project.AssignHive.services;


import com.project.AssignHive.entity.Subtask;

import java.util.List;

public interface SubTaskServices {
    Subtask addSubtask(String assignmentId, Subtask subtask);
    List<Subtask> getSubtasksByAssignmentId(String assignmentId);
    void deleteSubtask(String subtaskId);
}
