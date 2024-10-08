package com.fall2024devops.taskmanager.tasks.service;

import com.fall2024devops.taskmanager.common.exception.NotFoundException;
import com.fall2024devops.taskmanager.common.exception.UnauthorizedException;
import com.fall2024devops.taskmanager.common.utils.SecurityUtils;
import com.fall2024devops.taskmanager.tasks.dto.CreateTaskDTO;
import com.fall2024devops.taskmanager.tasks.dto.ListTasksDTO;
import com.fall2024devops.taskmanager.tasks.dto.UpdateTaskDTO;
import com.fall2024devops.taskmanager.tasks.entity.Task;
import com.fall2024devops.taskmanager.tasks.repository.TaskRepository;
import com.fall2024devops.taskmanager.tasks.service.TaskService;
import com.fall2024devops.taskmanager.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTask() {
        // Arrange
        CreateTaskDTO.Input input = new CreateTaskDTO.Input("Test Task", "Test Description");
        User currentUser = new User(); // Mock current user
        currentUser.setId(1L); // Set user ID as needed
        when(SecurityUtils.getCurrentUser()).thenReturn(Optional.of(currentUser));

        Task savedTask = new Task(); // Mock saved task
        savedTask.setId(1L);
        savedTask.setTitle(input.getTitle());
        savedTask.setDescription(input.getDescription());
        savedTask.setStatus("IN_PROGRESS");
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        // Act
        CreateTaskDTO.Output output = taskService.createTask(input);

        // Assert
        assertNotNull(output);
        assertEquals(1L, output.getId());
        assertEquals("Test Task", output.getTitle());
    }

    @Test
    void testGetTaskById() {
        // Arrange
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // Act
        ListTasksDTO.Output output = taskService.getTaskById(1L);

        // Assert
        assertNotNull(output);
        assertEquals("Test Task", output.getTitle());
    }

    @Test
    void testUpdateTaskNotFound() {
        // Arrange
        UpdateTaskDTO updateTasksDTO = new UpdateTaskDTO();
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> {
            taskService.updateTask(1L, updateTasksDTO);
        });
    }

    // Add more tests for other methods...
}
