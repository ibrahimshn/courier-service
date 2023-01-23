package com.migros.courierservice.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.migros.courierservice.model.entity.Store;
import com.migros.courierservice.repository.StoreRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Configuration
@AllArgsConstructor
public class StoreLoader implements CommandLineRunner {

    private final StoreRepository storeRepository;

    @Override
    public void run(String... args) {
        ObjectMapper mapper = new ObjectMapper();

        TypeReference<List<Store>> typeReference = new TypeReference<>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/static/stores.json");
        try {
            List<Store> stores = mapper.readValue(inputStream, typeReference);
            storeRepository.saveAll(stores);
            log.info("Stores saved!");
        } catch (IOException e){
            log.error("Unable to save stores: " + e.getMessage());
        }
    }

}
