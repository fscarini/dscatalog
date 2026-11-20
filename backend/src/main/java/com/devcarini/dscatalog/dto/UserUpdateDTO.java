package com.devcarini.dscatalog.dto;


import com.devcarini.dscatalog.entities.User;
import com.devcarini.dscatalog.services.validation.UserInsertValid;
import com.devcarini.dscatalog.services.validation.UserUpdateValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.stream.Collectors;

@UserUpdateValid
public record UserUpdateDTO(
        Long id,
        @NotBlank(message = "Campo obrigatório")
        String firstName,
        String lastName,
        @Email(message = "Por gentileza, digitar um e-mail válido")
        String email,
        Set<RoleDTO> roles
) {
    public UserUpdateDTO(User entity){
        this(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getRoles().stream()
                        .map(RoleDTO::new)
                        .collect(Collectors.toSet())
        );
    }
}
