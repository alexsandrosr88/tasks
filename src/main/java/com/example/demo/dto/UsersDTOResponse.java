package com.example.demo.dto;

import com.example.demo.entity.Users;
import lombok.Data;

@Data
public class UsersDTOResponse {
    private Integer id;
    private String name;
    private String email;
    private String password;

    public UsersDTOResponse(Users users){
        id = users.getId();
        name = users.getName();
        email = users.getEmail();
        password = users.getPassword();
    }
}
