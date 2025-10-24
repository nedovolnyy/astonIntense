package com.userservice.controller;

import com.userservice.dto.UserDto;
import com.userservice.service.UserService;
import com.userservice.utils.enums.OperationType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<?> getAll() {
        var userDtos = userService.getAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDtos);
    }

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User was found"),
        @ApiResponse(responseCode = "400", description = "User not found")
    })
    @GetMapping("/{id}")
    @Operation(summary = "Get user by Id")
    public ResponseEntity<?> getById(@Parameter(description = "User's Id", required = true)
            @PathVariable("id") Integer id) {
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

    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "User was saved successfully"),
        @ApiResponse(responseCode = "400", description = "User already exists")
    })
    @Operation(summary = "User creating")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserDto userDto) {
        var operationCode = userService.save(userDto);
        if (operationCode == OperationType.ERROR) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User already exists");
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Saved successfully");
    }

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User was updated"),
        @ApiResponse(responseCode = "400", description = "User not found")
    })
    @Operation(summary = "Update existing user")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody UserDto userDto,
            @Parameter(description = "User's Id", required = true) @PathVariable("id") Integer id) {
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

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User was deleted"),
        @ApiResponse(responseCode = "400", description = "User doesn't exists")
    })
    @DeleteMapping("/{id}")
    @Operation(summary = "Deleting user by Id")
    public ResponseEntity<?> delete(@Parameter(description = "User's Id", required = true)
            @PathVariable("id") Integer id) {
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
