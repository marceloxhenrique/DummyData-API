package com.api.dummydata.controllers;

import com.api.dummydata.models.User;
import com.api.dummydata.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/users")
@Tag(name = "User")
public class UserController {
    @Autowired
    UserService userService;
    
    @Operation(summary = "Get all users", description = "Retrieve all users, ordered by id")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(
        @RequestParam(defaultValue = "en") String lang,
        @PageableDefault(size = 100, sort = "id", direction = Sort.Direction.ASC)
        Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers(lang, pageable));
    }
    @Operation(summary = "Get a single user", description = "Retrieve one user by id")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
        @PathVariable String id,
        @RequestParam(defaultValue = "en") String lang) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(lang, id));
    }
}
