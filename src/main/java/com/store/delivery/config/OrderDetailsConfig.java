package com.store.delivery.config;

import com.store.delivery.entity.OrderDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@Configuration
@Slf4j
public class OrderDetailsConfig {

    @Bean
    public List<OrderDetails> orderDetailsList(ObjectMapper objectMapper) {
        try {
            log.info("Loading orders from orders.json");

            List<OrderDetails> orders = objectMapper.readValue(
                    new ClassPathResource("orders.json").getInputStream(),
                    new TypeReference<List<OrderDetails>>() {}
            );

            log.info("Successfully loaded {} orders", orders.size());
            return orders;

        } catch (IOException e) {
            log.error("Failed to load orders.json at startup", e);
            throw new RuntimeException("Failed to load orders at startup", e);
        }
    }
}
