package cl.duoc.ms_customers_bs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsCustomersBsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCustomersBsApplication.class, args);
	}

}
