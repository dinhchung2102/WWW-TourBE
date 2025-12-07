package fit.se.tourbe.features.auth.dto;

import fit.se.tourbe.features.auth.model.Customer.AuthProvider;
import fit.se.tourbe.features.auth.model.Customer.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {
    private int id;
    private String name;
    private String email;
    private String phone;
    private Role role;
    private Timestamp createdAt;
    private AuthProvider authProvider;
}

