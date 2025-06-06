package cl.duoc.ms_customers_bs.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cl.duoc.ms_customers_bs.model.dto.CustomerDto;

@FeignClient(name = "ms-customers-db", url = "http://localhost:8080/api/customers")

public interface CustomersDbFeignClient {

    @GetMapping("/authenticate/{username}/{password}")
    public boolean authenticateCustomerer (@PathVariable("username") String username, @PathVariable("password") String password);

    @GetMapping()
    public ResponseEntity<List<CustomerDto>> selectAllCustomer();

    @GetMapping("/GetCustomerById/{idCustomer}")
    public CustomerDto getCustomerById(@PathVariable("idCustomer") Long idCustomer);

    @PostMapping()
    public ResponseEntity<String> insertCustomer(@RequestBody CustomerDto customerDto);

    @DeleteMapping("/DeleteCustomerById/{idCustomer}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("idCustomer") Long idCustomer);
    
    
    @PutMapping("/UpdateCustomer")
    public ResponseEntity<String> updateCustomer(@RequestBody CustomerDto customerDto);
}
