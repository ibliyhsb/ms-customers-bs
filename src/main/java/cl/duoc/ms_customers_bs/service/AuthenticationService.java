package cl.duoc.ms_customers_bs.service;

import cl.duoc.ms_customers_bs.clients.CustomersDbFeignClient;
import cl.duoc.ms_customers_bs.model.dto.AuthenticationResponse;
import cl.duoc.ms_customers_bs.model.dto.CustomerDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private CustomersDbFeignClient customersDbFeignClient;

    private final SecretKey secretKey;

    @Value("${jwt.expiration:86400000}") // 24 horas por defecto
    private long jwtExpiration;

    public AuthenticationService(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public AuthenticationResponse login(String email, String password) {
        // Autenticar con la base de datos
        boolean isAuthenticated = customersDbFeignClient.authenticateCustomer(email, password);
        
        if (!isAuthenticated) {
            return new AuthenticationResponse(null, email, null, null, "Invalid credentials");
        }

        // Obtener información del usuario
        ResponseEntity<CustomerDto> customerResponse = customersDbFeignClient.getCustomerByEmail(email);
        
        if (customerResponse.getBody() == null) {
            return new AuthenticationResponse(null, email, null, null, "User not found");
        }

        CustomerDto customer = customerResponse.getBody();

        // Generar JWT
        String token = generateToken(customer);

        return new AuthenticationResponse(
            token,
            customer.getEmail(),
            customer.getIdCustomer(),
            customer.getRoles(),
            "Login successful"
        );
    }

    private String generateToken(CustomerDto customer) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", customer.getIdCustomer());
        claims.put("roles", customer.getRoles());
        claims.put("email", customer.getEmail());

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .subject(customer.getEmail())
                .claims(claims)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }
}
