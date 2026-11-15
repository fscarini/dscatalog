package com.devcarini.dscatalog.service;

import com.devcarini.dscatalog.dto.UserDTO;
import com.devcarini.dscatalog.dto.UserInsertDTO;
import com.devcarini.dscatalog.entity.User;
import com.devcarini.dscatalog.repository.RoleRepository;
import com.devcarini.dscatalog.repository.UserRepository;
import com.devcarini.dscatalog.service.exceptions.DatabaseException;
import com.devcarini.dscatalog.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {
        Page<User> result = UserRepository.findAll(pageable);
        return result.map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User result = UserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado!"));
        return new UserDTO(result);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity = UserRepository.save(entity);
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO update(UserDTO dto, Long id) {
        try {
            User entity = UserRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = UserRepository.save(entity);
            return new UserDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage() + ": Produto não encontrado!");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!UserRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado!");
        }

        try {
            UserRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }

    }

    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setEmail(dto.email());

        entity.getRoles().clear();
        entity.getRoles().addAll(
                dto.roles().stream()
                        .map(x -> roleRepository.getReferenceById(x.id()))
                        .collect(Collectors.toSet())
        );
    }

    private void copyDtoToEntity(UserInsertDTO dto, User entity) {
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setEmail(dto.email());
        entity.setPassword(passwordEncoder.encode(dto.password()));

        entity.getRoles().clear();
        entity.getRoles().addAll(
                dto.roles().stream()
                        .map(x -> roleRepository.getReferenceById(x.id()))
                        .collect(Collectors.toSet())
        );
    }
}
