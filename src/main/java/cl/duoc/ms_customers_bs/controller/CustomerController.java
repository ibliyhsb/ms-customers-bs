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
import cl.duoc.ms_customers_bs.model.dto.AuthenticationResponse;
import cl.duoc.ms_customers_bs.model.dto.CustomerDto;
import cl.duoc.ms_customers_bs.service.AuthenticationService;
import cl.duoc.ms_customers_bs.service.CustomerService;
import feign.FeignException.FeignClientException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customers", description = "API para la gestión de clientes")
public class CustomerController {
 
    @Autowired
    CustomerService customerService;

    @Autowired
    AuthenticationService authenticationService;

    @Operation(summary = "Obtener cliente por ID", security = @SecurityRequirement(name = "bearer-jwt"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @GetMapping("/GetCustomerById/{idCustomer}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> getCustomerById(@PathVariable("idCustomer") Long idCustomer){
    return customerService.getCustomerById(idCustomer);

}

    @Operation(summary = "Obtener todos los clientes (Solo ADMIN)", security = @SecurityRequirement(name = "bearer-jwt"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomerDto>> selectAllCustomers(){
        ResponseEntity<List<CustomerDto>> listaCustomerDto = customerService.selectAllCustomer();

        return listaCustomerDto;
    }

    @Operation(summary = "Login de usuario", description = "Endpoint público para autenticación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login exitoso"),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        AuthenticationResponse response = authenticationService.login(request.getEmail(), request.getPassword());
        
        if (response.getToken() == null) {
            return ResponseEntity.status(401).body(response);
        }
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authenticate/{email}/{password}")
    public boolean authenticateCustomerPost(@PathVariable("email") String email, @PathVariable("password") String password){
        return customerService.authenticateCustomer(email, password);
    }

    /**
     * @deprecated Use POST /api/customers/login with request body instead.
     * This endpoint exposes credentials in URL which is a security risk.
     */
    @Deprecated
    @GetMapping("/authenticate/{email}/{password}")
    public boolean authenticateCustomerLegacy(@PathVariable("email") String email, @PathVariable("password") String password){
        return customerService.authenticateCustomer(email, password);
    }

    @PostMapping("/authenticate")
    public boolean authenticateCustomer(@RequestBody AuthenticationRequest request){
        return customerService.authenticateCustomer(request.getEmail(), request.getPassword());
    }

    @GetMapping("/GetCustomerByEmail/{email}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> getCustomerByEmail(@PathVariable("email") String email){
        try{
            return customerService.getCustomerByEmail(email);
        }
        catch(FeignClientException feignClientException){
            return ResponseEntity.status(feignClientException.status()).body(feignClientException.contentUTF8());
        }
    }

    @PostMapping()
    public ResponseEntity<String> insertCustomer(@RequestBody CustomerDto customerDto){
        try{
            return customerService.insertCustomer(customerDto);
        }
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
