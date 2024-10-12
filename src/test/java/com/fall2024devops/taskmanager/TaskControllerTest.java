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

    //Testing the create task endpoint
    @Test
    void testCreateTask() {
        /*
        ***************** Arrange *******************************
        Preparing the input and output object for the createTask method
        Mocking taskService.CreateTask method
        */
        CreateTaskDTO.Input input = new CreateTaskDTO.Input("Test Task", "Test Description");
        CreateTaskDTO.Output output = new CreateTaskDTO.Output(1L, "Test Task", "Test Description", "IN_PROGRESS", 1L, null, null, null);
        when(taskService.createTask(any(CreateTaskDTO.Input.class))).thenReturn(output);

        /*
             * *********** Act ***************
             * Invoking the method to be tested
             */
        ResponseEntity<GenericResponse<CreateTaskDTO.Output>> response = taskController.createTask(input);

        /*
             *********** Assert ***************
            Making expected assertions to verify the output of the createTask method
             */
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Task created successfully", response.getBody().getMessage());
        assertEquals(output, response.getBody().getData());
    }

    //Testing the get task by id endpoint
    @Test
    void testGetTaskById() {
        /*
        ***************** Arrange *******************************
        Preparing the input and output object for the getTaskById method
        Mocking taskService.getByTaskById method
        */
        ListTasksDTO.Output task = new ListTasksDTO.Output(1L, "Test Task", "Test Description", "IN_PROGRESS", 1L, null, null);
        when(taskService.getTaskById(1L)).thenReturn(task);

        /*
             * *********** Act ***************
             * Invoking the method to be tested
             */
        ResponseEntity<GenericResponse<ListTasksDTO.Output>> response = taskController.getTaskById(1L);

        /*
             *********** Assert ***************
            Making expected assertions to verify the output of the getTaskById method
             */
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Task retrieved successfully", response.getBody().getMessage());
        assertEquals(task, response.getBody().getData());
    }

    //Testing the get all tasks endpoint
    @Test
    void testGetAllTasks() {
        /*
        ***************** Arrange *******************************
        Preparing the input and output object for the getAllTasks method
        Mocking taskService.GetAllTasks method
        */
        List<ListTasksDTO.Output> tasks = List.of(
                new ListTasksDTO.Output(1L, "Test Task 1", "Test Description 1", "IN_PROGRESS", 1L, null, null),
                new ListTasksDTO.Output(2L, "Test Task 2", "Test Description 2", "IN_PROGRESS", 1L, null, null)
        );
        when(taskService.getAllTasks()).thenReturn(tasks);

        /*
             * *********** Act ***************
             * Invoking the method to be tested
             */
        ResponseEntity<GenericResponse<List<ListTasksDTO.Output>>> response = taskController.getAllTasks();

        /*
             *********** Assert ***************
            Making expected assertions to verify the output of the createTask method
             */
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tasks retrieved successfully", response.getBody().getMessage());
        assertEquals(tasks, response.getBody().getData());
    }

    //Testing the update task endpoint
    @Test
    void testUpdateTask() {
        /*
        ***************** Arrange *******************************
        Preparing the input and output object for the updateTask method
        Mocking taskService.updateTask method
        */
        UpdateTaskDTO.Input input = new UpdateTaskDTO.Input("Test Task", "Test Description", "IN_PROGRESS");
        UpdateTaskDTO.Output output = new UpdateTaskDTO.Output(1L, "Test Task", "Test Description", "IN_PROGRESS", 1L, null, null);
        when(taskService.updateTask(1L, input)).thenReturn(output);

        /*
             * *********** Act ***************
             * Invoking the method to be tested
             */
        ResponseEntity<GenericResponse<UpdateTaskDTO.Output>> response = taskController.updateTask(1L, input);

        /*
             *********** Assert ***************
            Making expected assertions to verify the output of the updateTask method
             */
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Task updated successfully", response.getBody().getMessage());
        assertEquals(output, response.getBody().getData());
    }

    //Testing for the delete task endpoint
    @Test
    void testDeleteTask() {
        /*
             * *********** Act ***************
             * Invoking the deleteTask method that is to be tested
             */
        ResponseEntity<Void> response = taskController.deleteTask(1L);

        /*
             *********** Assert ***************
            Making expected assertions to verify the output of the deleteTask method
             */
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
