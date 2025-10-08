package org.kerago.keragobackend.service;

import jakarta.validation.Valid;
import org.kerago.keragobackend.dto.UserRegister;
import org.kerago.keragobackend.dto.UserResponse;
import org.kerago.keragobackend.dto.adminDTO.UserAdminRequest;
import org.kerago.keragobackend.enums.Role;
import org.kerago.keragobackend.exception.ResourceAlreadyExistsException;
import org.kerago.keragobackend.exception.ResourceNotFoundException;
import org.kerago.keragobackend.model.Users;
import org.kerago.keragobackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserResponse userRegister(UserRegister userRegister) {

        if (userRepository.existsByEmail(userRegister.email())) {
            throw new ResourceAlreadyExistsException("email already registered");
        }

        if (userRepository.existsByPhone(userRegister.phone())) {
            throw new ResourceAlreadyExistsException("phone number already registered");
        }

        Users users = new Users();
        users.setUsername(userRegister.username());
        users.setEmail(userRegister.email());
        users.setPassword(passwordEncoder.encode(userRegister.password()));
        users.setPhone(userRegister.phone());
        users.setRole(Role.USER);
        users.setCreated(LocalDateTime.now());

        Users newUser = userRepository.save(users);

        return new UserResponse(
                newUser.getId(),
                newUser.getUsername(),
                newUser.getEmail(),
                newUser.getRole()

        );
    }

    public UserResponse getUserById(Long id) {
        Users users = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found with id :" + id));
        return new UserResponse(
                users.getId(),
                users.getUsername(),
                users.getEmail(),
                users.getRole()

        );
    }

    public UserResponse updateUserDetails(UserRegister userRegister, Long id) {
        Users users = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        users.setUsername(userRegister.username());
        users.setPassword(passwordEncoder.encode(userRegister.password()));
        users.setEmail(userRegister.email());
        users.setPhone(userRegister.phone());

        Users saved = userRepository.save(users);

        return new UserResponse(saved.getId(),saved.getUsername(), saved.getEmail(),saved.getRole());
    }


    public List<UserResponse> getAllUser() {
        List<Users> allUsers = userRepository.findAll();
        return allUsers.stream().map(users -> new UserResponse(users.getId(),users.getUsername(), users.getEmail(), users.getRole())).toList();
    }


    public UserResponse deleteUserById(Long id) {
        Users users = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
        userRepository.delete(users);

        return new UserResponse(
                users.getId(),
                users.getUsername(),
                users.getEmail(),
                users.getRole()

        );
    }


    public UserResponse adminRegister(UserAdminRequest userAdminRequest) {
        if (userRepository.existsByEmail(userAdminRequest.email())) {
            throw new ResourceAlreadyExistsException("user already exist");
        }
        Users users = new Users();
        users.setUsername(userAdminRequest.username());
        users.setEmail(userAdminRequest.email());
        users.setPassword(passwordEncoder.encode(userAdminRequest.password()));
        users.setRole(userAdminRequest.role());
        users.setCreated(LocalDateTime.now());
        users.setPhone(userAdminRequest.phone());

        Users saveUsers = userRepository.save(users);

        return new UserResponse(
                saveUsers.getId(),
                saveUsers.getUsername(),
                saveUsers.getEmail(),
                saveUsers.getRole()
        );
    }
}
