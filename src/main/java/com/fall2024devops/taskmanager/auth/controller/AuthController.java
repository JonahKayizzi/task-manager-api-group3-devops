package com.fall2024devops.taskmanager.auth.controller;

import com.fall2024devops.taskmanager.auth.dto.LoginDTO;
import com.fall2024devops.taskmanager.auth.dto.RegisterDTO;
import com.fall2024devops.taskmanager.auth.service.AuthService;
import com.fall2024devops.taskmanager.common.response.GenericResponse;
import com.fall2024devops.taskmanager.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<GenericResponse<RegisterDTO.RegisterDTOOutput>> register(@RequestBody @Valid RegisterDTO.RegisterDTOInput registerDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(new GenericResponse<>("User registered successfully", userService.register(registerDto)));
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponse<LoginDTO.LoginDTOOutput>> login(@RequestBody @Valid LoginDTO.LoginDTOInput loginDto) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(new GenericResponse<>("User logged in successfully", authService.login(loginDto)));
    }

}