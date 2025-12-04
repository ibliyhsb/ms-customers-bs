package cl.duoc.ms_customers_bs.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import cl.duoc.ms_customers_bs.clients.CustomersDbFeignClient;
import cl.duoc.ms_customers_bs.model.dto.CustomerDto;
import cl.duoc.ms_customers_bs.security.CustomUserDetails;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private void setUpSecurityContext(String email, Long userId, String... roles) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        CustomUserDetails userDetails = new CustomUserDetails(email, userId, authorities);
        UsernamePasswordAuthenticationToken authentication = 
            new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void insertCustomer(){
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        CustomerDto customerDto = new CustomerDto(1L, "12345", "Catalina", "Rojas", "catd.rojas@duocuc.cl", roles);
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Customer created.");

        when(customersDbFeignClient.insertCustomer(customerDto)).thenReturn(expectedResponse);

        ResponseEntity<String> actualResponse = customerService.insertCustomer(customerDto);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals("Customer created.", actualResponse.getBody());
    }

    @Test
    void getCustomerById(){
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        CustomerDto customerDto = new CustomerDto(1L, "12345", "Catalina", "Rojas", "catd.rojas@duocuc.cl", roles);
        ResponseEntity<CustomerDto> expectedResponse = ResponseEntity.ok().body(customerDto);    

        setUpSecurityContext("catd.rojas@duocuc.cl", 1L, "ROLE_ADMIN");

        when(customersDbFeignClient.getCustomerById(customerDto.getIdCustomer())).thenReturn(expectedResponse);

        ResponseEntity<?> actualResponse = customerService.getCustomerById(customerDto.getIdCustomer());

        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
        
        SecurityContextHolder.clearContext();
    }

    @Test
    void selectAllCustomer(){
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        CustomerDto customerDto1 = new CustomerDto(1L, "12345", "Catalina", "Rojas", "catd.rojas@duocuc.cl", roles);
        CustomerDto customerDto2 = new CustomerDto(2L, "43434", "Alejandra", "Faguas", "alfaguas@duocuc.cl", roles);
        
        List<CustomerDto> listaCustomerDto = new ArrayList<>();

        listaCustomerDto.add(customerDto1);
        listaCustomerDto.add(customerDto2);

        ResponseEntity<List<CustomerDto>> expectedResponse = ResponseEntity.ok().body(listaCustomerDto);   
        
        when(customersDbFeignClient.selectAllCustomer()).thenReturn(expectedResponse);

        ResponseEntity<List<CustomerDto>> actualResponse = customerService.selectAllCustomer();

        assertEquals(expectedResponse.getBody(), actualResponse.getBody());



    }

    }

