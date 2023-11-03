package com.example.demo.controller;

import com.example.demo.dto.TaskDTORequest;
import com.example.demo.dto.TaskDTOResponse;
import com.example.demo.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<TaskDTOResponse> criarTask(@Valid @RequestBody TaskDTORequest taskDTO, BindingResult validacao){

        var task = service.criarTask(taskDTO, validacao);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(task.getId())
                .toUri();

        return ResponseEntity.created(location).body(task);
    }

    @GetMapping
    public List<TaskDTOResponse> listaTodasTasks(){
        return service.listarTodasTasks();
    }

    @GetMapping("{id}")
    public TaskDTOResponse obterTask(@PathVariable Integer id){
        return service.obterUmaTask(id);
    }

    @PutMapping("{id}")
    public TaskDTOResponse atualizaTask(@PathVariable Integer id,@Valid @RequestBody TaskDTORequest taskDTO,
                                        BindingResult validacao){
        return service.atualizaTask(id, taskDTO, validacao);
    }
    @PutMapping("/user/{userId}&{taskId}")
    public TaskDTOResponse atualizaUserTask(@PathVariable Integer userId, @PathVariable Integer taskId){
        return service.atualizaUserTask(userId, taskId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deletaTask(@PathVariable Integer id){
        service.removeTask(id);
    }
}
