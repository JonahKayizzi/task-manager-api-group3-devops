package com.fall2024devops.taskmanager.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MethodNotAllowedException extends ResponseStatusException {
    public MethodNotAllowedException(String reason) {
        super(HttpStatus.METHOD_NOT_ALLOWED, reason);
    }
}
