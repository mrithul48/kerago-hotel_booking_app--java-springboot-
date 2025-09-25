package org.kerago.keragobackend.controller;


import jakarta.validation.Valid;
import org.kerago.keragobackend.dto.UserRegister;
import org.kerago.keragobackend.dto.UserResponse;
import org.kerago.keragobackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> userRegister(@Valid @RequestBody UserRegister userRegister) {
        UserResponse userResponse = userService.userRegister(userRegister);
        if (userResponse != null) {
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRegister> updateUserDetails(@RequestBody UserRegister userRegister, @PathVariable Long id) {
        UserRegister updateuserRegister = userService.updateUserDetails(userRegister, id);
        return new ResponseEntity<>(updateuserRegister, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUserById(@PathVariable Long id) {
        try {
            UserResponse deletedUser = userService.deleteUserById(id);
            return new ResponseEntity<>(deletedUser, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
