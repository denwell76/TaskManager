package com.backendproject.taskmanager.domain;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long>{
    List<Task> findByUser(User user);
    List<Task> findByUserOrderByPriorityAsc(User user); 
}
