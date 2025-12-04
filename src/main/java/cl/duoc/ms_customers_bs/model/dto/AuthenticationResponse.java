package cl.duoc.ms_customers_bs.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationResponse {

    @JsonProperty("token")
    private String token;

    @JsonProperty("email")
    private String email;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("roles")
    private Set<String> roles;

    @JsonProperty("message")
    private String message;
}
