package pl.springacademy.carservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SaM3CarServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaM3CarServiceApplication.class, args);
    }
}
