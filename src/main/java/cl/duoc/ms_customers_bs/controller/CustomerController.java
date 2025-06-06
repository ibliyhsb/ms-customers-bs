package cl.duoc.ms_customers_bs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.ms_customers_bs.model.dto.CustomerDto;
import cl.duoc.ms_customers_bs.service.CustomerService;

@RestController
public class CustomerController {
 
    @Autowired
    CustomerService customerService;

    @GetMapping("/GetCustomerById/{idCustomer}")
    public CustomerDto getCustomerById(Long idCustomer){
        CustomerDto customerDto = customerService.getCustomerById(idCustomer);
    return customerDto;

}
}
