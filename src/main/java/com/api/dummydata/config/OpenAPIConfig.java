package com.api.dummydata.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "DummyData API", description = "Open API Documentation for DummyData API", version = "0.0.0"))
public class OpenAPIConfig {

}
