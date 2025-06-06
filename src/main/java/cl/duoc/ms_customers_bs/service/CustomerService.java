package cl.duoc.ms_customers_bs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cl.duoc.ms_customers_bs.clients.CustomersDbFeignClient;
import cl.duoc.ms_customers_bs.model.dto.CustomerDto;

@Service
public class CustomerService {

@Autowired
CustomersDbFeignClient customersDbFeignClient;

public CustomerDto getCustomerById(Long idCustomer){

    CustomerDto customerDto = customersDbFeignClient.getCustomerById(idCustomer);

    return customerDto;
}

public ResponseEntity<List<CustomerDto>> selectAllCustomer(){
    List<CustomerDto> listaCustomerDto = customersDbFeignClient.selectAllCustomer().getBody();
    
    return (listaCustomerDto != null)? new ResponseEntity<>(listaCustomerDto, HttpStatus.OK):
                                       new ResponseEntity<>(HttpStatus.NOT_FOUND);
}

public boolean authenticateCustomerer(String username, String password){
    
    return customersDbFeignClient.authenticateCustomerer(username, password);

}

public ResponseEntity<String> insertCustomer(CustomerDto customerDto){

    return customersDbFeignClient.insertCustomer(customerDto);
}

public ResponseEntity<String> deleteCustomer(Long idCustomer){
    
    return customersDbFeignClient.deleteCustomer(idCustomer);
}

public ResponseEntity<String> updateCustomer(CustomerDto customerDto){
    
    return customersDbFeignClient.updateCustomer(customerDto);
}

}

