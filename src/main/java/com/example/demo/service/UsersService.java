package com.example.demo.service;

import com.example.demo.dto.UsersDTORequest;
import com.example.demo.dto.UsersDTOResponse;
import com.example.demo.entity.Users;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.exception.RecursoNaoEncontrado;
import com.example.demo.service.exception.RegraDeNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Objects;

@Service
public class UsersService {

    @Autowired
    private UsersRepository repository;

    @Transactional(readOnly = false)
    public UsersDTOResponse criarUser(UsersDTORequest userDTO, BindingResult validacao){

        validaUser(validacao);

        return new UsersDTOResponse(repository.save(new Users(userDTO)));

    }

    @Transactional(readOnly = true)
    public List<UsersDTOResponse> listaTodosUsers(){
        return repository.findAll().stream()
                .map(UsersDTOResponse::new)
                .toList();
    }
    @Transactional(readOnly = true)
    public UsersDTOResponse obterUserPorId(Integer id){
        return new UsersDTOResponse(repository.findById(id).orElseThrow(()->
                new RecursoNaoEncontrado("O ID do user não foi encontrado!")));
    }

    @Transactional(readOnly = false)
    public UsersDTOResponse atualizaUser(Integer id, UsersDTORequest userDTO, BindingResult validacao){

            validaUser(validacao);

            Users userAtualizado = repository.findById(id).orElseThrow(()->
                    new RecursoNaoEncontrado("O ID do user não foi encontrado!"));

            userAtualizado.setName(userDTO.getName());
            userAtualizado.setPassword(userDTO.getPassword());
            userAtualizado.setEmail(userDTO.getEmail());

            return new UsersDTOResponse(repository.saveAndFlush(userAtualizado));




    }
    @Transactional(readOnly = false)
    public void removeUSer(Integer id){
        Users userParaRemocao = repository.findById(id).orElseThrow(()->
                new RecursoNaoEncontrado("O ID do user não foi encontrado!"));
        repository.delete(userParaRemocao);
    }

    private void validaUser(BindingResult validacao){
        if(validacao.hasErrors()){
            String campo = Objects.requireNonNull(validacao.getFieldError()).getField();

            switch (campo) {
                case "name", "password", "email" ->
                        throw new RegraDeNegocioException(validacao.getFieldError().getDefaultMessage());
            }
        }
    }

}
