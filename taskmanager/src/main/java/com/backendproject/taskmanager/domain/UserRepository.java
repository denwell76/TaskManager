package com.backendproject.taskmanager.domain;

import org.springframework.stereotype.Repository;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;



@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

