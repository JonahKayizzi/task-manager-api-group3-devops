package com.fall2024devops.taskmanager.tasks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ListTasksDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Input {
        private String searchTerm;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Output {
        private Long id;
        private String title;
        private String description;
        private String status;
        private Long userId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
