package cl.duoc.ms_customers_bs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cl.duoc.ms_customers_bs.clients.CustomersDbFeignClient;
import cl.duoc.ms_customers_bs.model.dto.CustomerDto;
import feign.FeignException;

@Service
public class CustomerService {

@Autowired
CustomersDbFeignClient customersDbFeignClient;

public ResponseEntity<?> getCustomerById(Long idCustomer) {
    try {
        ResponseEntity<CustomerDto> response = customersDbFeignClient.getCustomerById(idCustomer);

        if (response.getBody()==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This customer does not exist");
        }

        else {
        return response;
        }

  } catch (FeignException feignException) {
        return ResponseEntity.status(feignException.status()).body(feignException.contentUTF8());
    }
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

