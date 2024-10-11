package com.fall2024devops.taskmanager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fall2024devops.taskmanager.common.response.GenericResponse;
import com.fall2024devops.taskmanager.tasks.controller.TaskController;
import com.fall2024devops.taskmanager.tasks.dto.CreateTaskDTO;
import com.fall2024devops.taskmanager.tasks.dto.ListTasksDTO;
import com.fall2024devops.taskmanager.tasks.dto.UpdateTaskDTO;
import com.fall2024devops.taskmanager.tasks.service.TaskService;

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

    @Test
    void testGetAllTasks() {
        // Arrange
        List<ListTasksDTO.Output> tasks = List.of(
                new ListTasksDTO.Output(1L, "Test Task 1", "Test Description 1", "IN_PROGRESS", 1L, null, null),
                new ListTasksDTO.Output(2L, "Test Task 2", "Test Description 2", "IN_PROGRESS", 1L, null, null)
        );
        when(taskService.getAllTasks()).thenReturn(tasks);

        // Act
        ResponseEntity<GenericResponse<List<ListTasksDTO.Output>>> response = taskController.getAllTasks();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tasks retrieved successfully", response.getBody().getMessage());
        assertEquals(tasks, response.getBody().getData());
    }

    @Test
    void testUpdateTask() {
        // Arrange
        UpdateTaskDTO.Input input = new UpdateTaskDTO.Input("Test Task", "Test Description", "IN_PROGRESS");
        UpdateTaskDTO.Output output = new UpdateTaskDTO.Output(1L, "Test Task", "Test Description", "IN_PROGRESS", 1L, null, null);
        when(taskService.updateTask(1L, input)).thenReturn(output);

        // Act
        ResponseEntity<GenericResponse<UpdateTaskDTO.Output>> response = taskController.updateTask(1L, input);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Task updated successfully", response.getBody().getMessage());
        assertEquals(output, response.getBody().getData());
    }

    @Test
    void testDeleteTask() {
        // Act
        ResponseEntity<Void> response = taskController.deleteTask(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
