package org.mocker;

import org.mocker.config.MockConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableAsync
@EnableWebMvc
@RestController
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties({MockConfig.class})
public class MockerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockerApplication.class, args);
    }

}
