package com.fall2024devops.taskmanager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.fall2024devops.taskmanager.common.utils.SecurityUtils;
import com.fall2024devops.taskmanager.tasks.dto.CreateTaskDTO;
import com.fall2024devops.taskmanager.tasks.dto.ListTasksDTO;
import com.fall2024devops.taskmanager.tasks.dto.UpdateTaskDTO;
import com.fall2024devops.taskmanager.tasks.entity.Task;
import com.fall2024devops.taskmanager.tasks.repository.TaskRepository;
import com.fall2024devops.taskmanager.tasks.service.TaskService;
import com.fall2024devops.taskmanager.user.entity.User;

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
    void testGetAllTasks() {
        /*
        ***************** Arrange *******************************
        Preparing the list of tasks to be returned by the taskRepository
        */
        User currentUser = new User(); // Mock current user

        List<Task> tasks = Stream.of(
                new Task("Test Task 1", "Test Description 1", "IN_PROGRESS", currentUser),
                new Task("Test Task 2", "Test Description 2", "IN_PROGRESS", currentUser)
        ).collect(Collectors.toList());

        /*
        *********** Act ***************
        Mocking the behavour of the taskRepository to return the list of tasks
        This is done to avoid calling the actual database
        We know that the taskRepository will return the list of tasks
        that we have created above
        And we also know that the taskService will call the taskRepository.findAll() method
        */
        when(taskRepository.findAll()).thenReturn(tasks);

        // Invoking the method to be tested
        List<ListTasksDTO.Output> output = taskService.getAllTasks();

        /*
         * *********** Assert ***************
         * Making expected assertions to verify the output of the getAllTasks method
         */
        assertNotNull(output);
        assertEquals(2, output.size());
        assertEquals("Test Task 1", output.get(0).getTitle());
        assertEquals("Test Task 2", output.get(1).getTitle());
    }

    @Test
    void testCreateTask() {
        /*
        ***************** Arrange *******************************
        Preparing the input for the createTask method
        CreateTask uses the CreateTaskDTO.Input class to get the input
        */
        CreateTaskDTO.Input input = new CreateTaskDTO.Input();
        input.setTitle("Test Task");
        input.setDescription("Test Description");

        User currentUser = new User(); // Mock current user
        /*
        Mocking the behaviour of the SecurityUtils.getCurrentUser() method
        Because the taskService will call this method to get the current user
        We know that the SecurityUtils.getCurrentUser() method will return the currentUser
        We are mocking it to avoid calling the actual SecurityUtils.getCurrentUser() method
        This is done to avoid calling the actual database
        */
        try (MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)) {
            mockedSecurityUtils.when(SecurityUtils::getCurrentUser)
                .thenReturn(Optional.of(currentUser));

            /*
            Mocking the behaviour of the taskRepository.save() method
            Because the taskService will call this method to save the task
            We know that the taskRepository.save() method will return the saved task
            We are mocking it to avoid calling the actual database
            */
            Task savedTask = new Task(); // Mock saved task
            savedTask.setId(1L);
            savedTask.setTitle(input.getTitle());
            savedTask.setDescription(input.getDescription());
            savedTask.setStatus("IN_PROGRESS");
            savedTask.setUser(currentUser); // Set the user to avoid NullPointerException
            when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

            /*
             * *********** Act ***************
             * Invoking the method to be tested
             */
            CreateTaskDTO.Output output = taskService.createTask(input);

            /*
             *********** Assert ***************
            Making expected assertions to verify the output of the createTask method
             */
            assertNotNull(output);
            assertEquals(1L, output.getId());
            assertEquals("Test Task", output.getTitle());
        }
    }

    @Test
    void testGetTaskById() {
        /*
        ***************** Arrange *******************************
        Mocking the behaviour of the taskRepository.findById() method
        Because the taskService will call this method to get the task by id
        We know that the taskRepository.findById() method will return the task
        We are mocking it to avoid calling the actual database
        */
        User currentUser = new User(); // Mock current user
        Task task = new Task("Test Task", "Test Description", "IN_PROGRESS", currentUser);
        task.setId(1L);
        task.setTitle("Test Task");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        /*
         *********** Act ***************
        Invoking the method to be tested 
        */
        ListTasksDTO.Output output = taskService.getTaskById(1L);

        /*
         *********** Assert ***************
        Making expected assertions to verify the output of the getTaskById method
         */
        assertNotNull(output);
        assertEquals("Test Task", output.getTitle());
    }

    /* Testing when task is not found on getTaskById */
    @Test
    void getTaskByIdNotFound() {
        /*
        ***************** Arrange *******************************
        Mocking the behaviour of the taskRepository.findById() method
        Because the taskService will call this method to get the task by id
        We know that the taskRepository.findById() method will return the task
        We are mocking it to avoid calling the actual database
        */
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        /*
         *********** Act ***************
        Invoking the method to be tested 
        */
        try {
            taskService.getTaskById(1L);
        } catch (Exception e) {
        /*
         *********** Assert ***************
        Making expected assertions to verify the output of the getTaskById method for not found
         */
            assertEquals("404 NOT_FOUND \"Task not found\"", e.getMessage());
        }
    }

    /* Testing updateTask method */
    @Test
    void testUpdateTask() {
        /* 
        ***************** Arrange *******************************
        Preparing the input for the updateTask method
        UpdateTask uses the UpdateTaskDTO.Input class to get the input
        */
        UpdateTaskDTO.Input input = new UpdateTaskDTO.Input();
        input.setTitle("Updated Task");
        input.setDescription("Updated Description");
        input.setStatus("COMPLETED");

        /*
        Mocking the behaviour of the taskRepository.findById() method
        Because the taskService will call this method to get the task by id
        We know that the taskRepository.findById() method will return the task
        We are mocking it to avoid calling the actual database 
        */
        User currentUser = new User(); // Mock current user
        Task task = new Task("Test Task", "Test Description", "IN_PROGRESS", currentUser);
        task.setId(1L);
        task.setTitle("Test Task");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        /*
        Mocking the behaviour of the taskRepository.save() method
        Because the taskService will call this method to save the updated task
        We know that the taskRepository.save() method will return the updated task
        We are mocking it to avoid calling the actual database
        */
        Task updatedTask = new Task("Updated Task", "Updated Description", "COMPLETED", currentUser);
        updatedTask.setId(1L);
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);
        
        /*
        *********** Act ***************
        Invoking the method to be tested
         */
        UpdateTaskDTO.Output output = taskService.updateTask(1L, input);

        /*
        *********** Assert ***************
         */
        assertNotNull(output);
        assertEquals("Updated Task", output.getTitle());
        assertEquals("Updated Description", output.getDescription());
        assertEquals("COMPLETED", output.getStatus());      
        
    }

    /* Testing when task is not found on updateTask */
    @Test
    void updateTaskNotFound() {
        /*
        ***************** Arrange *******************************
        Preparing the input for the updateTask method
        UpdateTask uses the UpdateTaskDTO.Input class to get the input
        */
        UpdateTaskDTO.Input input = new UpdateTaskDTO.Input();
        input.setTitle("Updated Task");
        input.setDescription("Updated Description");
        input.setStatus("COMPLETED");

        /*
        Mocking the behaviour of the taskRepository.findById() method
        Because the taskService will call this method to get the task by id
        We know that the taskRepository.findById() method will return the task
        We are mocking it to avoid calling the actual database
        */
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        /*
        *********** Act ***************
        Invoking the method to be tested
         */
        try {
            taskService.updateTask(1L, input);
        } catch (Exception e) {
        /*
        *********** Assert ***************
        */
            assertEquals("404 NOT_FOUND \"Task not found\"", e.getMessage());
        }
    }

    /* Testing deleteTask method */
    @Test
    void testDeleteTask() {
        /*
        ***************** Arrange *******************************
        Mocking the behaviour of the taskRepository.findById() method
        Because the taskService will call this method to get the task by id
        We know that the taskRepository.findById() method will return the task
        We are mocking it to avoid calling the actual database
        */
        User currentUser = new User(); // Mock current user
        Task task = new Task("Test Task", "Test Description", "IN_PROGRESS", currentUser);
        task.setId(1L);
        task.setTitle("Test Task");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        /*
        *********** Act ***************
        Invoking the method to be tested
         */
        taskService.deleteTask(1L);

        /*
        *********** Assert ***************
        */
        assertNotNull(task);
    }

    /* Testing when task is not found on deleteTask */
    @Test
    void deleteTaskNotFound() {
        /*
        ***************** Arrange *******************************
        Mocking the behaviour of the taskRepository.findById() method
        Because the taskService will call this method to get the task by id
        We know that the taskRepository.findById() method will return the task
        We are mocking it to avoid calling the actual database
        */
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        /*
        *********** Act ***************
        Invoking the method to be tested
         */
        try {
            taskService.deleteTask(1L);
        } catch (Exception e) {
            /*
            *********** Assert ***************
            */
            assertEquals("404 NOT_FOUND \"Task not found\"", e.getMessage());
        }
    }

}
