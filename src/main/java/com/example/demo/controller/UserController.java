package com.example.demo.controller;

import com.example.demo.dto.UsersDTORequest;
import com.example.demo.dto.UsersDTOResponse;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UsersService service;

    @GetMapping
    public List<UsersDTOResponse> listaTodosUsers(){
        return service.listaTodosUsers();
    }

    @PostMapping
    public ResponseEntity<UsersDTOResponse> criarUser(UsersDTORequest userDTO){
        UsersDTOResponse newUsersDTO = service.criarUser(userDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(newUsersDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(newUsersDTO);
    }


}
