package com.api.dummydata.models;

import java.time.LocalDateTime;

import lombok.Data;
@Data
public class Comment {
  private int id;
  private int userId;
  private String title;
  private String body;
  private LocalDateTime createdAt;
}
