package com.api.dummydata.controllers;

import com.api.dummydata.services.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public JsonNode getAllUsers(
            @RequestParam(defaultValue = "en") String language,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable) throws IOException {
        return userService.getUsersByLanguage(language, pageable);
    }
    @GetMapping("/{id}")
    public JsonNode getUserById(
            @PathVariable String id,
            @RequestParam(defaultValue = "en") String language,
            Pageable pageable) throws IOException {
        return userService.getUserById(language, id);
    }

}
