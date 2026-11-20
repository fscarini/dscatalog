package com.devcarini.dscatalog.controllers;

import com.devcarini.dscatalog.dto.UserDTO;
import com.devcarini.dscatalog.dto.UserInsertDTO;
import com.devcarini.dscatalog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService UserService;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable){
        Page<UserDTO> result = UserService.findAll(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        UserDTO dto = UserService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserInsertDTO dto){
        UserDTO userDTO = UserService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userDTO.id()).toUri();
        return ResponseEntity.created(uri).body(userDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserDTO dto){
        dto = UserService.update(dto, id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        UserService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
