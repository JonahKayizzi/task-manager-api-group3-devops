package com.fall2024devops.taskmanager.tasks.repository;

import com.fall2024devops.taskmanager.tasks.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
