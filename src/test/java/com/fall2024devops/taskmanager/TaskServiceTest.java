
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.fall2024devops.taskmanager.tasks.dto.ListTasksDTO;
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
        // Arrange
        User currentUser = new User(); // Mock current user

        List<Task> tasks = Stream.of(
                new Task("Test Task 1", "Test Description 1", "IN_PROGRESS", currentUser),
                new Task("Test Task 2", "Test Description 2", "IN_PROGRESS", currentUser)
        ).collect(Collectors.toList());

        // Mock the behavour of the taskRepository to return the list of tasks
        // This is done to avoid calling the actual database
        // We know that the taskRepository will return the list of tasks
        // that we have created above
        // And we also know that the taskService will call the taskRepository.findAll() method
        when(taskRepository.findAll()).thenReturn(tasks);

        // Invoke the method to be tested
        List<ListTasksDTO.Output> output = taskService.getAllTasks();

        // Make expected assertions to verify the output of the getAllTasks method
        assertNotNull(output);
        assertEquals(2, output.size());
        assertEquals("Test Task 1", output.get(0).getTitle());
        assertEquals("Test Task 2", output.get(1).getTitle());
    }

    // Add more tests for other methods...
}
