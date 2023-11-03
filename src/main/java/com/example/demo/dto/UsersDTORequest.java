package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsersDTORequest{

    @Size(min = 2, max = 50, message = "{app.nome.size}")
    private String name;
    @Pattern(regexp = "^[A-Za-z0-9]{6,8}$", message = "{app.password.invalido}")
    private String password;
    @Email(message = "{app.email.invalido}")
    //Esse é válido melhor que o do Spring Boot
    //@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@.+\\..+$", message = "{app.email.invalido}")
    private String email;
}