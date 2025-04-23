package com.backendproject.taskmanager.domain.service;

import com.backendproject.taskmanager.domain.Task;
import com.backendproject.taskmanager.domain.TaskRepository;
import com.backendproject.taskmanager.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public void save(Task task) {
        if (task.getDescription() == null) {
            task.setDescription("");
        }
        taskRepository.save(task);
    }

    public List<Task> getTasksForUserSortedByPriority(User user) {
        return taskRepository.findByUserOrderByPriorityAsc(user);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                             .orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}