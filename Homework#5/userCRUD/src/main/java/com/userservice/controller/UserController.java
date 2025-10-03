package com.userservice.controller;

import com.userservice.dto.UserDto;
import com.userservice.service.UserService;
import com.userservice.utils.enums.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> getAll() {
        var userDtos = userService.getAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDtos);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        var userDto = userService.getById(id);
        if (userDto == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User not found");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDto);
    }

    @PostMapping("/users")
    public ResponseEntity<?> save(@RequestBody UserDto userDto) {
        var operationCode = userService.save(userDto);
        if (operationCode == OperationType.ERROR) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User already exists");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Saved successfully");
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@RequestBody UserDto userDto, @PathVariable("id") Integer id) {
        var operationCode = userService.update(userDto, id);
        if (operationCode == OperationType.ERROR) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User not found");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Updated successfully");
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        var operationCode = userService.delete(id);
        if (operationCode == OperationType.ERROR) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User doesn't exists");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Deleted successfully");
    }
}
