package com.devcarini.dscatalog.dto;

import com.devcarini.dscatalog.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;
import java.util.stream.Collectors;

public record UserDTO(
        Long id,
        @NotBlank(message = "Campo obrigatório")
        String firstName,
        String lastName,
        @Email(message = "Por gentileza, digitar um e-mail válido")
        String email,
        Set<RoleDTO> roles
) {

    public UserDTO(User entity){
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
