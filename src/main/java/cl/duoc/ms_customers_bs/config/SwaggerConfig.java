package cl.duoc.ms_customers_bs.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Customers microservice - Business(BS) Documentation")
                        .version("1.0")
                        .description("This microservice is responsible for the business logic"));
    }
}
