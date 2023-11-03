package com.example.demo.controller;

import com.example.demo.dto.UsersDTORequest;
import com.example.demo.dto.UsersDTOResponse;
import com.example.demo.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("{id}")
    public UsersDTOResponse obterUserPorId(@PathVariable Integer id){
        return service.obterUserPorId(id);
    }
    @PostMapping
    public ResponseEntity<UsersDTOResponse> criarUser(@Valid @RequestBody UsersDTORequest userDTO,
                                                      BindingResult validacao){

            UsersDTOResponse newUsersDTO = service.criarUser(userDTO, validacao);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("{id}")
                    .buildAndExpand(newUsersDTO.getId())
                    .toUri();

            return ResponseEntity.created(location).body(newUsersDTO);
    }

    @PutMapping("{id}")
    public UsersDTOResponse atualizaUser(@PathVariable Integer id,
                                         @Valid @RequestBody UsersDTORequest userDTO, BindingResult validacao ){
        return service.atualizaUser(id, userDTO,validacao);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deletaUserPorId(@PathVariable Integer id){
        service.removeUSer(id);
    }

}
