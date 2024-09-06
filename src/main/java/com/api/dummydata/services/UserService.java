package com.api.dummydata.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class UserService {
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    ObjectMapper objectMapper;

    public JsonNode getUsersByLanguage(String language, Pageable pageable) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:user_" + language + ".json");
        File file = resource.getFile();
        JsonNode usersFile = objectMapper.readTree(file);
        ArrayNode users = objectMapper.createArrayNode();
        int userQuantity = Math.min(10, pageable.getPageSize());
        for (int i = 0; i < userQuantity; i++) {
            users.add(usersFile.get(i));
        }
        return users;
    }

    public JsonNode getUserById(String language, String id) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:user_" + language + ".json");
        File file = resource.getFile();
        JsonNode usersFile = objectMapper.readTree(file);
        for (JsonNode userById : usersFile) {
            if (userById.get("id").asText().equals(id)) {
                return userById;
            }
        }
        return objectMapper.createArrayNode();
    }
}
