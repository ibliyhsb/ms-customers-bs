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

public ResponseEntity<String> getCustomerById(Long idCustomer){
    CustomerDto customerDto = customersDbFeignClient.getCustomerById(idCustomer).getBody();

    if (customerDto==null){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This ID does not exist");
    }

    return ResponseEntity.ok("ID Customer: " 
                              + customerDto.getIdCustomer()
                              + "\n"
                              + "Username: " 
                              + customerDto.getUsername() 
                              + "\n" 
                              + "Password: " 
                              + customerDto.getPassword() 
                              + "\n" 
                              + "Name: " 
                              +  customerDto.getName() 
                              + "\n" 
                              + "Last name: " 
                              + customerDto.getLastName() 
                              +  "\n" 
                              + "Email: " 
                              + customerDto.getEmail());
}

public ResponseEntity<List<CustomerDto>> selectAllCustomer(){
    List<CustomerDto> listaCustomerDto = customersDbFeignClient.selectAllCustomer().getBody();
    
    return (listaCustomerDto != null)? new ResponseEntity<>(listaCustomerDto, HttpStatus.OK):
                                       new ResponseEntity<>(HttpStatus.NOT_FOUND);
}


public boolean authenticateCustomer(String username, String password){    
    return customersDbFeignClient.authenticateCustomer(username, password);
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

