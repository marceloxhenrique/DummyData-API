package com.api.dummydata.controllers;


import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.api.dummydata.models.Comment;
import com.api.dummydata.models.Image;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ImageControllerTest {
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private WebTestClient webTestClient;
    private final String urlBase = "/api/images";

    private List<Image> readImagesFromJson(String lang) {
        Resource resource = resourceLoader.getResource("classpath:imagesData/images_" + lang + ".json");
        try {
            InputStream inputStream = resource.getInputStream();
            return Arrays.asList(objectMapper.readValue(inputStream, Image[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("Shoudl get all Images")
    @Test
    public void getAllImages() {
        webTestClient.get().uri(urlBase)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Comment.class)
                .hasSize(100);
    }

    @DisplayName("Should get all Images in French")
    @Test
    public void getAllImagesInFrench() {
        List<Image> imageJson = readImagesFromJson("fr");
        String imageTitle = imageJson.get(0).getTitle();
        webTestClient.get().uri(urlBase + "?lang=fr")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Image.class)
                .hasSize(100)
                .consumeWith(response -> {
                    List<Image> images = response.getResponseBody();
                    assertNotNull(images);
                    assertEquals(images.get(0).getTitle(), imageTitle);
                });
    }

    @DisplayName("Should get all Images in Portuguese")
    @Test
    public void getAllImagesInPortuguese() {
        List<Image> imageJson = readImagesFromJson("pt");
        String imageTitle = imageJson.get(0).getTitle();
        webTestClient.get().uri(urlBase + "?lang=pt")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Image.class)
                .hasSize(100)
                .consumeWith(response -> {
                    List<Image> images = response.getResponseBody();
                    assertNotNull(images);
                    assertEquals(images.get(0).getTitle(), imageTitle);
                });
    }

    @DisplayName("Should get all Images in English")
    @Test
    public void getAllImagesInEnglish() {
        List<Image> imageJson = readImagesFromJson("en");
        String imageTitle = imageJson.get(0).getTitle();
        webTestClient.get().uri(urlBase + "?lang=en")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Image.class)
                .hasSize(100)
                .consumeWith(response -> {
                    List<Image> images = response.getResponseBody();
                    assertNotNull(images);
                    assertEquals(images.get(0).getTitle(), imageTitle);
                });
    }

    @DisplayName("Should get 10 Images")
    @Test
    public void getTenImages() {

        webTestClient.get().uri(urlBase + "?size=10")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Image.class)
                .hasSize(10);
    }

    @DisplayName("Should get 10 Images in French")
    @Test
    public void getTenImagesInFrench() {
        List<Image> imageJson = readImagesFromJson("fr");
        String imageTitle = imageJson.get(0).getTitle();
        webTestClient.get().uri(urlBase + "?size=10&lang=fr")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Image.class)
                .hasSize(10)
                .consumeWith(response -> {
                    List<Image> images = response.getResponseBody();
                    assertNotNull(images);
                    Image firstImage = images.get(0);
                    assertEquals(imageTitle, firstImage.getTitle());
                });
    }

    @DisplayName("Should get Images by Id")
    @Test
    public void getTenImagesById() {
        webTestClient.get().uri(urlBase + "/{1}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Image.class)
                .consumeWith(response -> {
                    Image image = response.getResponseBody();
                    assertNotNull(image);
                    assertEquals(image.getId(), 1);
                });
    }

    @DisplayName("Should get Images by Id and by language (French)")
    @Test
    public void getTenImagesByIdInFrench() {
        List<Image> imageJson = readImagesFromJson("fr");
        String firstImageTitle = imageJson.get(0).getTitle();
        webTestClient.get().uri(urlBase + "/{1}?lang=fr", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Image.class)
                .consumeWith(response -> {
                    Image image = response.getResponseBody();
                    assertNotNull(image);
                    assertEquals(image.getId(), 1);
                    assertEquals(image.getTitle(), firstImageTitle);
                });
    }

    @DisplayName("Should return 404 for non-existing Image by id")
    @Test
    public void getCommentByNonExistingId() {
        webTestClient.get().uri(urlBase + "/999")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();

    }
}
