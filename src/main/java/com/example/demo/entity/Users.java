package com.example.demo.entity;

import com.example.demo.dto.UsersDTORequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "tab_users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String password;

    public Users(UsersDTORequest dto){
        name = dto.getName();
        email = dto.getEmail();
        password = dto.getPassword();
    }
}
