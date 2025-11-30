package cl.duoc.ms_customers_bs.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import cl.duoc.ms_customers_bs.model.dto.CustomerDto;
import cl.duoc.ms_customers_bs.service.CustomerService;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    public CustomerControllerTest(){
    MockitoAnnotations.openMocks(this);

    }

    @Test
    void insertCustomer(){
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        CustomerDto customerDto = new CustomerDto(1L, "catdrojas", "12345", "Catalina", "Rojas", "catd.rojas@duocuc.cl", roles);
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Customer created.");

        when(customerService.insertCustomer(customerDto)).thenReturn(expectedResponse);

        ResponseEntity<String> actualResponse = customerController.insertCustomer(customerDto);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals("Customer created.", actualResponse.getBody());
    }

    @Test
    void getCustomerById(){
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        CustomerDto customerDto = new CustomerDto(1L, "catdrojas", "12345", "Catalina", "Rojas", "catd.rojas@duocuc.cl", roles);
        ResponseEntity<?> expectedResponse = ResponseEntity.ok().body(customerDto);    

        when(customerService.getCustomerById(customerDto.getIdCustomer())).thenReturn((ResponseEntity)expectedResponse);

        ResponseEntity<?> actualResponse = customerController.getCustomerById(customerDto.getIdCustomer());

        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
    }

    @Test
    void selectAllCustomer(){
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        CustomerDto customerDto1 = new CustomerDto(1L, "catdrojas", "12345", "Catalina", "Rojas", "catd.rojas@duocuc.cl", roles);
        CustomerDto customerDto2 = new CustomerDto(2L, "alfaguas", "43434", "Alejandra", "Faguas", "alfaguas@duocuc.cl", roles);
        
        List<CustomerDto> listaCustomerDto = new ArrayList<>();

        listaCustomerDto.add(customerDto1);
        listaCustomerDto.add(customerDto2);

        ResponseEntity<List<CustomerDto>> expectedResponse = ResponseEntity.ok().body(listaCustomerDto);   
        
        when(customerService.selectAllCustomer()).thenReturn(expectedResponse);

        ResponseEntity<List<CustomerDto>> actualResponse = customerController.selectAllCustomers();

        assertEquals(expectedResponse.getBody(), actualResponse.getBody());



    }

}
