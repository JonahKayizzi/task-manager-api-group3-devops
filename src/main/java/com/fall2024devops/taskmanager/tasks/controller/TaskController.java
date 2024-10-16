package com.fall2024devops.taskmanager.tasks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fall2024devops.taskmanager.common.response.GenericResponse;
import com.fall2024devops.taskmanager.tasks.dto.CreateTaskDTO;
import com.fall2024devops.taskmanager.tasks.dto.ListTasksDTO;
import com.fall2024devops.taskmanager.tasks.dto.UpdateTaskDTO;
import com.fall2024devops.taskmanager.tasks.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //Create task Endpoint
    @PostMapping
    public ResponseEntity<GenericResponse<CreateTaskDTO.Output>> createTask(@RequestBody @Valid CreateTaskDTO.Input input) {
        CreateTaskDTO.Output createdTask = taskService.createTask(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse<>("Task created successfully", createdTask));
    }

    //Get Task By Id Endpoint
    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<ListTasksDTO.Output>> getTaskById(@PathVariable Long id) {
        ListTasksDTO.Output task = taskService.getTaskById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>("Task retrieved successfully", task));
    }

    //Get All Tasks Endpoint
    @GetMapping
    public ResponseEntity<GenericResponse<List<ListTasksDTO.Output>>> getAllTasks() {
        List<ListTasksDTO.Output> tasks = taskService.getAllTasks();
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>("Tasks retrieved successfully", tasks));
    }

    //Update Task Endpoint
   @PatchMapping("/{id}")
   public ResponseEntity<GenericResponse<UpdateTaskDTO.Output>> updateTask(@PathVariable Long id, @Valid UpdateTaskDTO.Input taskDto) {
       UpdateTaskDTO.Output updatedTask = taskService.updateTask(id, taskDto);
       return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>("Task updated successfully", updatedTask));
   }

   //Delete Task by Id Endpoint
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
       taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
   }
}
