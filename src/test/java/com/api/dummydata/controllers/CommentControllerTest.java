package com.api.dummydata.controllers;

import com.api.dummydata.models.Comment;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class CommentControllerTest {
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private WebTestClient webTestClient;
    private final String urlBase = "/api/comments";

    private List<Comment> readCommentsFromJson(String lang){
        Resource resource = resourceLoader.getResource("classpath:commentData/comment_" + lang + ".json");
        try {
            InputStream inputStream = resource.getInputStream();
            return Arrays.asList(objectMapper.readValue(inputStream, Comment[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("Should get all Comments")
    @Test
    public void getAllComments() {
        webTestClient.get().uri(urlBase)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Comment.class)
                .hasSize(100);
    }

    @DisplayName("Should get all Comments in French")
    @Test
    public void getAllCommentsInFrench() {
        List<Comment> commentsJson = readCommentsFromJson("fr");
        String title = commentsJson.get(0).getTitle();
        webTestClient.get().uri(urlBase + "?lang=fr")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Comment.class)
                .hasSize(100)
                .consumeWith(response -> {
                    List<Comment> comment = response.getResponseBody();
                    assertNotNull(comment);
                    assertEquals(comment.get(0).getTitle(), title);
                });
    }

    @DisplayName("Should get all Comments in Portuguese")
    @Test
    public void getAllCommentsInPortuguese() {
        List<Comment> commentsJson = readCommentsFromJson("pt");
        String title = commentsJson.get(0).getTitle();
        webTestClient.get().uri(urlBase + "?lang=pt")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Comment.class)
                .hasSize(100)
                .consumeWith(response -> {
                    List<Comment> comment = response.getResponseBody();
                    assertNotNull(comment);
                    assertEquals(comment.get(0).getTitle(), title);
                });
    }

    @DisplayName("Should get all Comments in English")
    @Test
    public void getAllCommentsInEnglish() {
        List<Comment> commentsJson = readCommentsFromJson("en");
        String title = commentsJson.get(0).getTitle();
        webTestClient.get().uri(urlBase + "?lang=en")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Comment.class)
                .hasSize(100)
                .consumeWith(response -> {
                    List<Comment> comment = response.getResponseBody();
                    assertNotNull(comment);
                    assertEquals(comment.get(0).getTitle(), title);
                });
    }

    @DisplayName("Should get 10 comments")
    @Test
    public void getTenComments() {
        webTestClient.get().uri(urlBase + "?size=10")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Comment.class)
                .hasSize(10);
    }

    @DisplayName("Should get 10 Comments in French")
    @Test
    public void getTenCommentsInFrench(){
        List<Comment> commentsJson = readCommentsFromJson("fr");
        String title = commentsJson.get(0).getTitle();
        webTestClient.get().uri(urlBase + "?size=10&lang=fr")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Comment.class)
                .hasSize(10)
                .consumeWith(response -> {
                    List<Comment> comments = response.getResponseBody();
                    assertNotNull(comments);
                    Comment firsComment = comments.get(0);
                    assertEquals(firsComment.getTitle(), title);
                    
                });
    }

    @DisplayName("Should get Comment by id")
    @Test
    public void getCommentById(){
        webTestClient.get().uri(urlBase + "/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Comment.class)
                .consumeWith(response -> {
                    Comment comment = response.getResponseBody();
                    assertNotNull(comment);
                    assertEquals(comment.getId(), 1);
                });
    }

    @DisplayName("Should get Comment by id and by language (French)")
    @Test
    public void getCommentByIdInFrench(){
        List<Comment> commentJson = readCommentsFromJson("fr");
        String firstCommentTitle = commentJson.get(0).getTitle();
        webTestClient.get().uri(urlBase + "/{id}?lang=fr", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Comment.class)
                .consumeWith(response -> {
                    Comment comment = response.getResponseBody();
                    assertNotNull(comment);
                    assertEquals(comment.getId(), 1);
                    assertEquals(comment.getTitle(), firstCommentTitle);
                });
    }
    
    @DisplayName("Should return 404 for not-existing Comment by Id")
    @Test
    public void getCommentByNonExistingId(){
        webTestClient.get().uri(urlBase + "/999")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

}
