package com.example.demo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UsersDTORequest{
    private String name;
    private String email;
    private String password;
}
