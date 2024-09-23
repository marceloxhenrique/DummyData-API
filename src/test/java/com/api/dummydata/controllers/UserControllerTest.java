package com.api.dummydata.controllers;

import com.api.dummydata.models.User;
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
class UserControllerTest {
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private WebTestClient webTestClient;
    private final String urlBase = "/api/users";

    private List<User> readUsersFromJson(String lang) {
        Resource resource = resourceLoader.getResource("classpath:userData/user_" + lang + ".json");
        try {
            InputStream inputStream = resource.getInputStream();
            return Arrays.asList(objectMapper.readValue(inputStream, User[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("Should get all Users")
    @Test
    public void getAllUsers() {
        webTestClient.get().uri(urlBase)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .hasSize(100);
    }

    @DisplayName("Should get all Users in French")
    @Test
    public void getAllUsersInFrench() {
        List<User> usersJson = readUsersFromJson("fr");
        String country = usersJson.get(0).getAddress().getCountry();
        webTestClient.get().uri(urlBase + "?lang=fr")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .hasSize(100)
                .consumeWith(users -> {
                    List<User> response = users.getResponseBody();
                    assertNotNull(response);
                    assertEquals(response.get(0).getAddress().getCountry(), country);
                });
    }

    @DisplayName("Should get all Users in Portuguese")
    @Test
    public void getAllUsersInPortuguese() {
        List<User> usersJson = readUsersFromJson("pt");
        String country = usersJson.get(0).getAddress().getCountry();
        webTestClient.get().uri(urlBase + "?lang=pt")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .hasSize(100)
                .consumeWith(users -> {
                    List<User> response = users.getResponseBody();
                    assertNotNull(response);
                    assertEquals(response.get(0).getAddress().getCountry(), country);
                });
    }

    @DisplayName("Should get all Users in English")
    @Test
    public void getAllUsersInEnglish() {
        List<User> usersJson = readUsersFromJson("en");
        String country = usersJson.get(0).getAddress().getCountry();
        webTestClient.get().uri(urlBase + "?lang=en")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .hasSize(100)
                .consumeWith(users -> {
                    List<User> response = users.getResponseBody();
                    assertNotNull(response);
                    assertEquals(response.get(0).getAddress().getCountry(), country);
                });
    }

    @DisplayName("Should get 10 Users")
    @Test
    public void getTenUsers() {
        webTestClient.get().uri(urlBase + "?size=10")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .hasSize(10);
    }

    @DisplayName("Should get 10 Users in French")
    @Test
    public void getTenUsersInFrench() {
        List<User> usersJson = readUsersFromJson("fr");
        String country = usersJson.get(0).getAddress().getCountry();
        webTestClient.get().uri(urlBase + "?size=10&lang=fr")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .hasSize(10)
                .consumeWith(response -> {
                    List<User> users = response.getResponseBody();
                    assertNotNull(users);
                    User firstUser = users.get(0);
                    assertEquals(firstUser.getAddress().getCountry(), country);
                });
    }

    @DisplayName("Should get User by id")
    @Test
    public void getUserById() {
        webTestClient.get().uri(urlBase + "/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .consumeWith(response -> {
                    User user = response.getResponseBody();
                    assertNotNull(user);
                    assertEquals(1, user.getId());
                });
    }

    @DisplayName("Should get User by id and by language (French)")
    @Test
    public void getUserByIdInFrench() {
        List<User> usersJson = readUsersFromJson("fr");
        String firstname = usersJson.get(0).getFirstName();
        webTestClient.get().uri(urlBase + "/{id}?lang=fr", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .consumeWith(response -> {
                    User user = response.getResponseBody();
                    assertNotNull(user);
                    assertEquals(firstname, user.getFirstName());
                });
    }

    @DisplayName("Should return 404 for non-existing User by id")
    @Test
    public void getUserByNonExistingId() {
        webTestClient.get().uri(urlBase + "/{id}", 123)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

}