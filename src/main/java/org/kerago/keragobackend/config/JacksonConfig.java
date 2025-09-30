package org.kerago.keragobackend.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // Ignore null/empty fields during serialization
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        // Add support for Java 8 date/time types
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
