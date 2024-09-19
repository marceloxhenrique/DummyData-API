package com.api.dummydata.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.dummydata.exceptions.ImageAmountNotSupportedException;
import com.api.dummydata.exceptions.ImageNotFoundException;
import com.api.dummydata.exceptions.LanguageNotSupportedException;
import com.api.dummydata.models.Image;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ImageService {
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    ObjectMapper objectMapper;
    private static final List<String> SUPPORTED_LANGUAGE = Arrays.asList("en", "pt", "fr");

    private boolean IsLanguageSuported(String lang) {
        return SUPPORTED_LANGUAGE.contains(lang);
    }

    private boolean IsQuantityImageSupported(int imageQuantity) {
        return imageQuantity > 0 && imageQuantity <= 100;
    }

    public List<Image> getAllImages(String lang, Pageable pageable) {
        int imageQuantity = pageable.getPageSize();
        if (!this.IsLanguageSuported(lang)) throw new LanguageNotSupportedException();
        if (!this.IsQuantityImageSupported(imageQuantity)) throw new ImageAmountNotSupportedException();
        try {
            Resource resource = resourceLoader.getResource("classpath:/imagesData/images_" + lang + ".json");
            InputStream inputStream = resource.getInputStream();
            List<Image> images = Arrays.asList(objectMapper.readValue(inputStream, Image[].class));
            return images.stream()
                    .limit(imageQuantity)
                    .toList();
        } catch (IOException e) {
            throw new LanguageNotSupportedException("Error reading the image_" + lang + ".json file." + e);
        }
    }

    public Image getImageById(String lang, String id) {
        if (!this.IsLanguageSuported(lang)) throw new LanguageNotSupportedException();
        try {
            Resource resource = resourceLoader.getResource("classpath:/imagesData/images_" + lang + ".json");
            InputStream inputStream = resource.getInputStream();
            List<Image> images = Arrays.asList(objectMapper.readValue(inputStream, Image[].class));
            return images.stream()
                    .filter(image -> Integer.toString(image.getId()).equals(id))
                    .findFirst()
                    .orElseThrow(() -> new ImageNotFoundException("Image with ID " + id + " not found!"));
        } catch (IOException e) {
            throw new LanguageNotSupportedException("Error reading the image_" + lang + ".json file." + e);
        }
    }
}
