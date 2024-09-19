package com.api.dummydata.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.dummydata.exceptions.CommentAmmountNotSupportedException;
import com.api.dummydata.exceptions.CommentNotFoundException;
import com.api.dummydata.exceptions.LanguageNotSupportedException;
import com.api.dummydata.models.Comment;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class CommentService {
  @Autowired
  ResourceLoader resourceLoader;
  @Autowired
  ObjectMapper objectMapper;

  private static final List<String> SUPPORTED_LANGUAGE = Arrays.asList("en", "pt", "fr");

  private boolean IsLanguageSuported (String lang){
    return SUPPORTED_LANGUAGE.contains(lang);
  }
  private boolean IsQuantityCommentSupported (int commentQuantity){
    return commentQuantity > 0 && commentQuantity <= 100;
  }

  public List<Comment> getAllComments(String lang, Pageable pageable) {
    int commentQuantity = pageable.getPageSize();
    if(!IsLanguageSuported(lang)) throw new LanguageNotSupportedException();
    if(!IsQuantityCommentSupported(commentQuantity)) throw new CommentAmmountNotSupportedException();
    try {
      Resource resource = resourceLoader.getResource("classpath:commentData/comment_" + lang + ".json");
      InputStream inputStream = resource.getInputStream();
      List<Comment> comments = Arrays.asList(objectMapper.readValue(inputStream, Comment[].class));
      return comments.stream()
        .limit(commentQuantity)
        .toList();
    } catch (IOException e) {
      throw new  LanguageNotSupportedException("Error reading the comment_" + lang + ".json file. " +  e);
    }
  }

  public Comment getCommentById(String lang, String id) {
    if(!this.IsLanguageSuported(lang)) throw new LanguageNotSupportedException();
    try {
      Resource resource = resourceLoader.getResource("classpath:commentData/comment_" + lang + ".json");
      InputStream inputStream = resource.getInputStream();
      List<Comment> comments = Arrays.asList(objectMapper.readValue(inputStream, Comment[].class));
      return comments.stream()
        .filter(comment -> Integer.toString(comment.getId()).equals(id))
        .findFirst()
        .orElseThrow(() -> new CommentNotFoundException());
    } catch (IOException e) {
      throw new  LanguageNotSupportedException("Error reading the comment_" + lang + ".json file. " +  e);
    }
  }
}
