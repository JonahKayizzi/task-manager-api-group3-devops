package com.fall2024devops.taskmanager.tasks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fall2024devops.taskmanager.common.exception.NotFoundException;
import com.fall2024devops.taskmanager.common.exception.UnauthorizedException;
import com.fall2024devops.taskmanager.common.utils.SecurityUtils;
import com.fall2024devops.taskmanager.tasks.dto.CreateTaskDTO;
import com.fall2024devops.taskmanager.tasks.dto.ListTasksDTO;
import com.fall2024devops.taskmanager.tasks.dto.UpdateTaskDTO;
import com.fall2024devops.taskmanager.tasks.entity.Task;
import com.fall2024devops.taskmanager.tasks.repository.TaskRepository;
import com.fall2024devops.taskmanager.user.entity.User;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public CreateTaskDTO.Output createTask(CreateTaskDTO.Input input) {
        User currentUser = SecurityUtils.getCurrentUser()
                .orElseThrow(() -> new UnauthorizedException("User not authenticated"));

        Task task = Task.builder()
                .title(input.getTitle())
                .description(input.getDescription())
                .status("IN_PROGRESS")
                .user(currentUser)
                .build();

        Task savedTask = taskRepository.save(task);

        return new CreateTaskDTO.Output(
                savedTask.getId(),
                savedTask.getTitle(),
                savedTask.getDescription(),
                savedTask.getStatus(),
                savedTask.getUser().getId(),
                savedTask.getCreatedAt(),
                savedTask.getUpdatedAt(),
                savedTask.getDeletedAt()
        );
    }

    public ListTasksDTO.Output getTaskById(Long id) {
        Task foundTask = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
        return new ListTasksDTO.Output(
                foundTask.getId(),
                foundTask.getTitle(),
                foundTask.getDescription(),
                foundTask.getStatus(),
                foundTask.getUser().getId(),
                foundTask.getCreatedAt(),
                foundTask.getUpdatedAt()
        );
    }

    public UpdateTaskDTO.Output updateTask(Long id, UpdateTaskDTO.Input input) {
        // Fetch the task by id
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));

        // Update task fields
        if (input.getTitle() != null) {
            task.setTitle(input.getTitle());
        }
        if (input.getDescription() != null) {
            task.setDescription(input.getDescription());
        }
        if (input.getStatus() != null) {
            task.setStatus(input.getStatus());
        }

        // save the updated task
        Task updatedTask = taskRepository.save(task);

        // Return the updated task in Output format
        return new UpdateTaskDTO.Output(
                updatedTask.getId(),
                updatedTask.getTitle(),
                updatedTask.getDescription(),
                updatedTask.getStatus(),
                updatedTask.getUser().getId(),
                updatedTask.getCreatedAt(),
                updatedTask.getUpdatedAt()
        );
    }

    public List<ListTasksDTO.Output> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(task -> new ListTasksDTO.Output(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getUser().getId(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        ))
                .toList();
    }
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found"));
        
        taskRepository.delete(task);
    }
}
