package com.api.dummydata.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.api.dummydata.models.Image;
import com.api.dummydata.services.ImageService;

@RestController
@RequestMapping("/api/images")
public class ImageController {
  @Autowired
  ImageService imageService;

  @GetMapping
  public ResponseEntity<List<Image>> getAllImages(
    @RequestParam(defaultValue = "en") String lang,
    @PageableDefault(size = 100, sort = "id", direction = Sort.Direction.ASC)
    Pageable pageable){
    return ResponseEntity.status(HttpStatus.OK).body(imageService.getAllImages(lang, pageable));
  }
  @GetMapping("{id}")
  public ResponseEntity<Image> getImagesById(
    @PathVariable String id,
    @RequestParam(defaultValue = "en") String lang){
    return ResponseEntity.status(HttpStatus.OK).body(imageService.getImageById(lang, id));
  }
}
