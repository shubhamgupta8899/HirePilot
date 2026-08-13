package com.shubham.HirePilot.auth.dto;

import com.shubham.HirePilot.user.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be Valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be 8 character")
    private String password;

    @NotNull(message = "Role is required")
    private Role role;
}
