package com.aidan.collectionservice;

import com.aidan.collectionservice.client.ProductClient;
import com.aidan.security.client.UserClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients(clients = {UserClient.class, ProductClient.class})
@EnableScheduling
@EnableDiscoveryClient
public class CollectionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollectionServiceApplication.class, args);
    }

}
