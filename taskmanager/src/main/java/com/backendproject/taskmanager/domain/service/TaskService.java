package com.backendproject.taskmanager.domain.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendproject.taskmanager.domain.Task;
import com.backendproject.taskmanager.domain.TaskRepository;
import com.backendproject.taskmanager.domain.User;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasksForUser(User user) {
        return taskRepository.findByUser(user);
    }

    public List<Task> getTasksForUserSortedByPriority(User user) {
        return taskRepository.findByUserOrderByPriorityAsc(user);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }

    public void save(Task task) {
        taskRepository.save(task);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}