package com.fall2024devops.taskmanager.tasks.controller;

import com.fall2024devops.taskmanager.common.response.GenericResponse;
import com.fall2024devops.taskmanager.tasks.controller.TaskController;
import com.fall2024devops.taskmanager.tasks.dto.CreateTaskDTO;
import com.fall2024devops.taskmanager.tasks.dto.ListTasksDTO;
import com.fall2024devops.taskmanager.tasks.dto.UpdateTaskDTO;
import com.fall2024devops.taskmanager.tasks.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTask() {
        // Arrange
        CreateTaskDTO.Input input = new CreateTaskDTO.Input("Test Task", "Test Description");
        CreateTaskDTO.Output output = new CreateTaskDTO.Output(1L, "Test Task", "Test Description", "IN_PROGRESS", 1L, null, null, null);
        when(taskService.createTask(any(CreateTaskDTO.Input.class))).thenReturn(output);

        // Act
        ResponseEntity<GenericResponse<CreateTaskDTO.Output>> response = taskController.createTask(input);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Task created successfully", response.getBody().getMessage());
        assertEquals(output, response.getBody().getData());
    }

    @Test
    void testGetTaskById() {
        // Arrange
        ListTasksDTO.Output task = new ListTasksDTO.Output(1L, "Test Task", "Test Description", "IN_PROGRESS", 1L, null, null);
        when(taskService.getTaskById(1L)).thenReturn(task);

        // Act
        ResponseEntity<GenericResponse<ListTasksDTO.Output>> response = taskController.getTaskById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Task retrieved successfully", response.getBody().getMessage());
        assertEquals(task, response.getBody().getData());
    }

    // Add more tests for other controller methods...
}
