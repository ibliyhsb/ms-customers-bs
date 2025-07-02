package cl.duoc.ms_customers_bs.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import cl.duoc.ms_customers_bs.clients.CustomersDbFeignClient;
import cl.duoc.ms_customers_bs.model.dto.CustomerDto;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomersDbFeignClient customersDbFeignClient;

    @InjectMocks
    private CustomerService customerService;

    public CustomerServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void insertCustomer(){
        CustomerDto customerDto = new CustomerDto(1L, "catdrojas", "12345", "Catalina", "Rojas", "catd.rojas@duocuc.cl");
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Customer created.");

        when(customersDbFeignClient.insertCustomer(customerDto)).thenReturn(expectedResponse);

        ResponseEntity<String> actualResponse = customerService.insertCustomer(customerDto);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals("Customer created.", actualResponse.getBody());
    }

    @Test
    void getCustomerById(){
        CustomerDto customerDto = new CustomerDto(1L, "catdrojas", "12345", "Catalina", "Rojas", "catd.rojas@duocuc.cl");
        ResponseEntity<CustomerDto> expectedResponse = ResponseEntity.ok().body(customerDto);    

        when(customersDbFeignClient.getCustomerById(customerDto.getIdCustomer())).thenReturn(expectedResponse);

        ResponseEntity<?> actualResponse = customerService.getCustomerById(customerDto.getIdCustomer());

        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
    }

    @Test
    void selectAllCustomer(){
        CustomerDto customerDto1 = new CustomerDto(1L, "catdrojas", "12345", "Catalina", "Rojas", "catd.rojas@duocuc.cl");
        CustomerDto customerDto2 = new CustomerDto(2L, "alfaguas", "43434", "Alejandra", "Faguas", "alfaguas@duocuc.cl");
        
        List<CustomerDto> listaCustomerDto = new ArrayList<>();

        listaCustomerDto.add(customerDto1);
        listaCustomerDto.add(customerDto2);

        ResponseEntity<List<CustomerDto>> expectedResponse = ResponseEntity.ok().body(listaCustomerDto);   
        
        when(customersDbFeignClient.selectAllCustomer()).thenReturn(expectedResponse);

        ResponseEntity<List<CustomerDto>> actualResponse = customerService.selectAllCustomer();

        assertEquals(expectedResponse.getBody(), actualResponse.getBody());



    }

    }

