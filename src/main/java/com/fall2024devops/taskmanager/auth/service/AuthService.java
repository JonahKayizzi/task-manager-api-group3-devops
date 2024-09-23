package com.fall2024devops.taskmanager.auth.service;

import com.fall2024devops.taskmanager.auth.dto.LoginDTO;
import com.fall2024devops.taskmanager.common.exception.UnauthorizedException;
import com.fall2024devops.taskmanager.common.services.JwtService;
import com.fall2024devops.taskmanager.user.entity.User;
import com.fall2024devops.taskmanager.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginDTO.LoginDTOOutput login(LoginDTO.LoginDTOInput loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials please try again");
        }

        // Generate JWT token
        String token = jwtService.generateToken(user);

        // Construct the output DTO
        LoginDTO.LoginDTOOutput output = new LoginDTO.LoginDTOOutput();
        output.setAccessToken(token);
        return output;
    }
}
