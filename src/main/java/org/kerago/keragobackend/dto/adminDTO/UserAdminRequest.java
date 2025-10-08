package org.kerago.keragobackend.dto.adminDTO;

import jakarta.validation.constraints.NotBlank;
import org.kerago.keragobackend.enums.Role;

public record UserAdminRequest(
    @NotBlank(message = "username not blank")
    String username,
    @NotBlank(message = "email not blank")
    String email,
    @NotBlank(message = "password not blank")
    String password,
    @NotBlank(message = "phone number not blank")
    String phone,
    Role role
){

}
