package pl.springacademy.carservice.configuration;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cglib.core.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("car")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("pl.springacademy.carservice.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Car service API for SpringAcademy course")
                .description("Car-service REST Api with Swagger")
                .build();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void displaySwaggerUrl() {
        System.out.println("Swagger-Ui url: http://localhost:8080/swagger-ui/index.html");
    }
}
