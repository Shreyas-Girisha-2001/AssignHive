package com.project.AssignHive.services;

import com.project.AssignHive.entity.Assignment;
import java.util.List;

public interface AssignmentServices {
    Assignment createAssignment(String subjectName, String createdBy, Assignment assignment);
    Assignment updateAssignment(String subjectName, String assignmentName, String createdBy, Assignment updatedAssignment);
    void deleteAssignment(String subjectName, String assignmentName, String createdBy);
    List<Assignment> getAssignmentsBySubject(String subjectName, String createdBy);
    List<Assignment> getAssignmentsByUser(String username);
    void setCompleted(String subjectName, String assignmentName, String createdBy);
    void inCompleted(String subjectName, String assignmentName, String createdBy);

}