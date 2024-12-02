package ru.andrey.ServerGameManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;

import java.util.Random;

@SpringBootApplication
public class ServerGameManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerGameManagementApplication.class, args);
    }

    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public Jedis jedis() {
        return new Jedis("redis", 6379);
    }

}
