package cl.duoc.ms_customers_bs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.ms_customers_bs.model.dto.AuthenticationRequest;
import cl.duoc.ms_customers_bs.model.dto.CustomerDto;
import cl.duoc.ms_customers_bs.service.CustomerService;
import feign.FeignException.FeignClientException;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
 
    @Autowired
    CustomerService customerService;

    @GetMapping("/GetCustomerById/{idCustomer}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> getCustomerById(@PathVariable("idCustomer") Long idCustomer){
    return customerService.getCustomerById(idCustomer);

}

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomerDto>> selectAllCustomers(){
        ResponseEntity<List<CustomerDto>> listaCustomerDto = customerService.selectAllCustomer();

        return listaCustomerDto;
    }

    /**
     * @deprecated Use POST /api/customers/authenticate with request body instead.
     * This endpoint exposes credentials in URL which is a security risk.
     */
    @Deprecated
    @GetMapping("/authenticate/{username}/{password}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public boolean authenticateCustomerLegacy(@PathVariable("username") String username, @PathVariable("password") String password){
        return customerService.authenticateCustomer(username, password);
    }

    @PostMapping("/authenticate")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public boolean authenticateCustomer(@RequestBody AuthenticationRequest request){
        return customerService.authenticateCustomer(request.getUsername(), request.getPassword());
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> insertCustomer(@RequestBody CustomerDto customerDto){
        try{
        return customerService.insertCustomer(customerDto);}
        catch(FeignClientException feignClientException){
            return ResponseEntity.status(feignClientException.status()).body(feignClientException.contentUTF8());
        }
    }

    @DeleteMapping("/DeleteCustomerById/{idCustomer}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCustomer(@PathVariable("idCustomer") Long idCustomer){
        try{
        return customerService.deleteCustomer(idCustomer);}
        catch(FeignClientException feignClientException){
            return ResponseEntity.status(feignClientException.status()).body(feignClientException.contentUTF8());

        }
    }

    @PutMapping("/UpdateCustomer")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> updateCustomer(@RequestBody CustomerDto customerDto){
        try{
        return customerService.updateCustomer(customerDto);}
        catch(FeignClientException feignClientException){
            return ResponseEntity.status(feignClientException.status()).body(feignClientException.contentUTF8());
        }
    }
}
