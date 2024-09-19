package com.api.dummydata.controllers;

import com.api.dummydata.models.Comment;
import com.api.dummydata.services.CommentService;

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
public class CommentController {
  @Autowired
  CommentService commentService;
  
  @GetMapping
  public ResponseEntity<List<Comment>>getAllComments(
    @RequestParam(defaultValue = "en") String lang,
    @PageableDefault(size = 100, sort = "id", direction = Sort.Direction.ASC)
    Pageable pageable){
    return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllComments(lang, pageable));
    }
  @GetMapping("/{id}")
  public ResponseEntity<Comment> getCommentById(
    @PathVariable String id,
    @RequestParam(defaultValue = "en") String lang) {
    return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentById(lang, id));
  }
}
