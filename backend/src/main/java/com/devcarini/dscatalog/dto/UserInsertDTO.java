package com.devcarini.dscatalog.dto;


import com.devcarini.dscatalog.entities.User;
import com.devcarini.dscatalog.services.validation.UserInsertValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.stream.Collectors;

@UserInsertValid
public record UserInsertDTO(
        Long id,
        @NotBlank(message = "Campo obrigatório")
        String firstName,
        String lastName,
        String password,
        @Email(message = "Por gentileza, digitar um e-mail válido")
        String email,
        Set<RoleDTO> roles
) {
    public UserInsertDTO(User entity){
        this(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPassword(),
                entity.getEmail(),
                entity.getRoles().stream()
                        .map(RoleDTO::new)
                        .collect(Collectors.toSet())
        );
    }
}
