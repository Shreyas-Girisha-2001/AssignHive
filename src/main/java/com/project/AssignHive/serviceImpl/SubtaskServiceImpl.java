package com.project.AssignHive.serviceImpl;

import com.project.AssignHive.entity.Subtask;
import com.project.AssignHive.repository.SubTaskRepository;
import com.project.AssignHive.services.SubTaskServices;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubtaskServiceImpl implements SubTaskServices {

    private final SubTaskRepository subtaskRepository;

    public SubtaskServiceImpl(SubTaskRepository subtaskRepository) {
        this.subtaskRepository = subtaskRepository;
    }

    @Override
    public Subtask addSubtask(String assignmentId, Subtask subtask) {
        subtask.setAssignmentId(assignmentId);
        return subtaskRepository.save(subtask);
    }

    @Override
    public List<Subtask> getSubtasksByAssignmentId(String assignmentId) {
        return subtaskRepository.findByAssignmentId(assignmentId);
    }

    @Override
    public void deleteSubtask(String subtaskId) {
        subtaskRepository.deleteById(subtaskId);
    }
}
