package com.example.demo.service;

import com.example.demo.dto.UsersDTORequest;
import com.example.demo.dto.UsersDTOResponse;
import com.example.demo.entity.Users;
import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersRepository repository;

    @Transactional(readOnly = false)
    public UsersDTOResponse criarUser(UsersDTORequest userDTO){
        if (userDTO != null){
            return new UsersDTOResponse(repository.save(new Users(userDTO)));
        }
        throw new RuntimeException();
    }

    @Transactional(readOnly = true)
    public List<UsersDTOResponse> listaTodosUsers(){
        return repository.findAll().stream()
                .map(UsersDTOResponse::new)
                .toList();
    }
    @Transactional(readOnly = true)
    public UsersDTOResponse obterUmUser(Integer id){
        return new UsersDTOResponse(repository.findById(id).orElseThrow());
    }

    @Transactional(readOnly = false)
    public UsersDTOResponse atualizaUser(Integer id, UsersDTORequest userDTO){
        if(userDTO != null || id != null) {
            Users userAtualizado = repository.findById(id).orElseThrow();

            userAtualizado.setName(userDTO.getName());
            userAtualizado.setPassword(userDTO.getPassword());
            userAtualizado.setEmail(userDTO.getEmail());
            return new UsersDTOResponse(repository.save(userAtualizado));
        }

        throw new RuntimeException();

    }
    @Transactional(readOnly = false)
    public void removeUSer(Integer id){
        Users userParaRemocao = repository.findById(id).orElseThrow();
        repository.delete(userParaRemocao);
    }


}
