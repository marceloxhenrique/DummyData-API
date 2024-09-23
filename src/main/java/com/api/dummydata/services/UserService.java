package com.api.dummydata.services;

import com.api.dummydata.exceptions.UsersAmmountNotSupportedException;
import com.api.dummydata.models.User;
import com.api.dummydata.exceptions.LanguageNotSupportedException;
import com.api.dummydata.exceptions.UserNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    ObjectMapper objectMapper;
    private static final List<String> SUPPORTED_LANGUAGE = Arrays.asList("en", "pt", "fr");

    private boolean isLanguageSupported(String lang) {
        return SUPPORTED_LANGUAGE.contains(lang);
    }

    private boolean isQuantityUserSupported(int userQuantity) {
        return userQuantity > 0 && userQuantity <= 100;
    }

    public List<User> getAllUsers(String lang, Pageable pageable) {
        int userQuantity = pageable.getPageSize();
        if (!this.isLanguageSupported(lang)) throw new LanguageNotSupportedException();
        if (!this.isQuantityUserSupported(userQuantity)) throw new UsersAmmountNotSupportedException();

        try {
            Resource resource = resourceLoader.getResource("classpath:userData/user_" + lang + ".json");
            InputStream inputStream = resource.getInputStream();
            List<User> users = Arrays.asList(objectMapper.readValue(inputStream, User[].class));
            return users.stream()
                    .limit(userQuantity)
                    .toList();

        } catch (IOException e) {
            throw new LanguageNotSupportedException("Error reading the user_" + lang + ".json file." + e);
        }
    }

    public User getUserById(String lang, String id) {
        if (!this.isLanguageSupported(lang)) throw new LanguageNotSupportedException();

        try {
            Resource resource = resourceLoader.getResource("classpath:userData/user_" + lang + ".json");
            InputStream inputStream = resource.getInputStream();
            List<User> users = Arrays.asList(objectMapper.readValue(inputStream, User[].class));
            return users.stream()
                    .filter(user -> Integer.toString(user.getId()).equals(id))
                    .findFirst()
                    .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

        } catch (IOException e) {
            throw new LanguageNotSupportedException("Error reading the user_" + lang + ".json file." + e);
        }
    }
}
