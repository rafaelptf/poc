package br.com.manager;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Created by rpeixoto on 02/08/15.
 */
@SpringBootApplication
public class AddressManagerMainApp {

    public static void main(String[] args) {
        SpringApplication.run(AddressManagerMainApp.class, args);
    }

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        builder.failOnEmptyBeans(false);
        return builder;
    }
}
