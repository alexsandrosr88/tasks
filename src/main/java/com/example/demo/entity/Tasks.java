package com.example.demo.entity;

import com.example.demo.dto.TaskDTORequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private String status;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Users users;

    public Tasks(TaskDTORequest dto){
        title = dto.getTitle();
        description = dto.getDescription();;
        status = dto.getStatus();
        users = new Users(dto.getUsers());
    }
}
