package com.example.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskDTORequest{

    @Size(min = 4, max = 50, message = "{app.title.size}")
    private String title;
    @Size(min = 10, max = 50, message = "{app.description.size}")
    private String description;
    @Size(min = 10, max = 50, message = "{app.status.size}")
    private String status;
    private UsersDTORequest users;
}
