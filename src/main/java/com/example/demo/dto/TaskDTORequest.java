package com.example.demo.dto;

import lombok.Data;

@Data
public class TaskDTORequest{
    private String title;
    private String description;
    private String status;
    private UsersDTORequest users;
}
