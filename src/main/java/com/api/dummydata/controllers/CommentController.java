package com.api.dummydata.controllers;

import com.api.dummydata.models.Comment;
import com.api.dummydata.services.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@Tag(name = "Comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    
    @Operation(summary = "Get all comments", description = "Retrieve all comments, ordered by id")
    @GetMapping
    public ResponseEntity<List<Comment>>getAllComments(
        @RequestParam(defaultValue = "en") String lang,
        @PageableDefault(size = 100, sort = "id", direction = Sort.Direction.ASC)
        Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllComments(lang, pageable));
        }

    @Operation(summary = "Get a single comment", description = "Retrieve one comment by id")    
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(
        @PathVariable String id,
        @RequestParam(defaultValue = "en") String lang) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentById(lang, id));
    }
}
