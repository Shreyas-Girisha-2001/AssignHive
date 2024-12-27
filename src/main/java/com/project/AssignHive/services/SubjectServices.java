package com.project.AssignHive.services;

import com.project.AssignHive.entity.Subject;

import java.util.List;

public interface SubjectServices {
    Subject createSubjectForUser(String username, Subject subject);
    List<Subject> getSubjectsForUser(String username);
    Subject updateSubjectForUser(String username, String subjectName, Subject updatedSubject);
    void deleteSubjectForUser(String username, String subjectName);
}
