package com.example.demo.dto;

import com.example.demo.entity.Tasks;
import com.example.demo.entity.Users;
import lombok.Data;

@Data
public class TaskDTOResponse {
    private Integer id;
    private String title;
    private String description;
    private String status;
    private UsersDTOResponse users;

    public TaskDTOResponse(Tasks tasks) {
        id = tasks.getId();
        title = tasks.getTitle();
        description = tasks.getDescription();
        status = tasks.getStatus();
        users = new UsersDTOResponse(tasks.getUsers());
    }
}
