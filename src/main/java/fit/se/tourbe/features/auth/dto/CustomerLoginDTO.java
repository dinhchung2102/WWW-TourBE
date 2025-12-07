package fit.se.tourbe.features.auth.dto;

import lombok.Data;

@Data
public class CustomerLoginDTO {
    private String email;
    private String password;
}
