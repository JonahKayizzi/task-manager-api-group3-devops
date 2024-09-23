package com.fall2024devops.taskmanager.user.service;

import com.fall2024devops.taskmanager.auth.dto.RegisterDTO;
import com.fall2024devops.taskmanager.common.exception.ConflictException;
import com.fall2024devops.taskmanager.user.entity.User;
import com.fall2024devops.taskmanager.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterDTO.RegisterDTOOutput register(RegisterDTO.RegisterDTOInput registerDto) {
        // Check if the user already exists
        if (userRepository.findByEmail(registerDto.getEmail()).isPresent()) {
            throw new ConflictException("User with this email already exists");
        }

        // Hash the password
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());

        // Create a new User entity
        User user = User.builder()
                .andrewId(registerDto.getAndrewId())
                .email(registerDto.getEmail())
                .password(encodedPassword)
                .build();

        // Save the user to the repository
        user = userRepository.save(user);

        // Construct the output DTO
        RegisterDTO.RegisterDTOOutput output = new RegisterDTO.RegisterDTOOutput();
        output.setId(user.getId());
        output.setAndrewId(user.getAndrewId());
        output.setEmail(user.getEmail());
        output.setCreatedAt(user.getCreatedAt());
        output.setUpdatedAt(user.getUpdatedAt());
        output.setDeletedAt(user.getDeletedAt());

        return output;
    }
}
