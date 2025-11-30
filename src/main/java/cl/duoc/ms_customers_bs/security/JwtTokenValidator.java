package cl.duoc.ms_customers_bs.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class JwtTokenValidator {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenValidator.class);

    private final SecretKey secretKey;

    public JwtTokenValidator(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            logger.error("JWT validation failed: {}", e.getMessage());
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    @SuppressWarnings("unchecked")
    public Set<String> getRolesFromToken(String token) {
        Claims claims = getClaims(token);
        List<String> roles = claims.get("roles", List.class);
        return roles != null ? new HashSet<>(roles) : new HashSet<>();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("userId", Long.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
