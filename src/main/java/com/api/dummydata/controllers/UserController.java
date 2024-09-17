package com.api.dummydata.controllers;

import com.api.dummydata.models.User;
import com.api.dummydata.services.UserService;
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
public class UserController {
  @Autowired
  UserService userService;
  
  @GetMapping
  public ResponseEntity<List<User>> getAllUsers(
    @RequestParam(defaultValue = "en") String lang,
    @PageableDefault(size = 100, sort = "id", direction = Sort.Direction.ASC)
    Pageable pageable) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers(lang, pageable)); 
  }
  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(
    @PathVariable String id,
    @RequestParam(defaultValue = "en") String lang) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(lang, id));
  }
}
