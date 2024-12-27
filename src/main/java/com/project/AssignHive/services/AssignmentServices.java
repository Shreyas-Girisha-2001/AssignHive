package com.project.AssignHive.services;

import com.project.AssignHive.entity.Assignment;

import java.util.List;

public interface AssignmentServices {

    /**
     * Adds a new assignment to a specific subject.
     * @param subjectId the ID of the subject to which the assignment belongs.
     * @param assignment the assignment to be added.
     * @return the saved assignment.
     */
    Assignment addAssignment(String subjectId, Assignment assignment);

    /**
     * Fetches all assignments for a specific subject.
     * @param subjectId the ID of the subject.
     * @return a list of assignments for the given subject.
     */
    List<Assignment> getAssignmentsBySubjectId(String subjectId);

    /**
     * Deletes an assignment by its ID.
     * @param assignmentId the ID of the assignment to delete.
     */
    void deleteAssignment(String assignmentId);
}
