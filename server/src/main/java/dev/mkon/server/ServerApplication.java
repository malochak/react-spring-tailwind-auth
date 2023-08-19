package dev.mkon.server;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import dev.mkon.server.api.security.model.user.DefaultUserDetailsService;

@Slf4j
@SpringBootApplication
public class ServerApplication {

    private final DefaultUserDetailsService userDetailsService;

    ServerApplication(DefaultUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            var response = userDetailsService.save("konrad@mkon.dev", "test");

            log.info("response: [{}] {}", response.getStatusCode(), response.getBody());
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
