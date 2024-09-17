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
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
  @Autowired
  ResourceLoader resourceLoader;
  @Autowired
  ObjectMapper objectMapper;
  private static final List<String> SUPPORTED_LANGUAGE = Arrays.asList("en", "pt", "fr");
  private boolean IsLanguageSuported (String lang){
    return SUPPORTED_LANGUAGE.contains(lang);
  }
  private boolean IsQuantityUserSupported (int userQuantity){
    return userQuantity > 0 && userQuantity <= 100;
  }
  
  public List<User> getUsers(String lang, Pageable pageable) {
    int userQuantity = pageable.getPageSize();
    if(!this.IsLanguageSuported(lang)) throw new LanguageNotSupportedException();
    if (!this.IsQuantityUserSupported(userQuantity)) throw new UsersAmmountNotSupportedException();
    try {
      Resource resource = resourceLoader.getResource("classpath:userData/user_" + lang + ".json");
      File file = resource.getFile();
      List<User> users = Arrays.asList(objectMapper.readValue(file, User[].class));
      return users.stream()
        .limit(userQuantity)
        .toList();
    } catch (IOException e) {
      throw new LanguageNotSupportedException("Error reading the user_"+ lang + ".json file." + e); 
    }
  }

  public User getUserById(String lang, String id) {
    if(!this.IsLanguageSuported(lang)) throw new LanguageNotSupportedException();
    try {
      Resource resource = resourceLoader.getResource("classpath:userData/user_" + lang + ".json");
      File jsonFile = resource.getFile();
      List<User> users = Arrays.asList(objectMapper.readValue(jsonFile, User[].class));
      return users.stream()
        .filter(user -> Integer.toString(user.getId()).equals(id))
        .findFirst()
        .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
    } catch (IOException e) {
      throw new LanguageNotSupportedException("Error reading the user_"+ lang + ".json file." + e); 
    }
  }
}
