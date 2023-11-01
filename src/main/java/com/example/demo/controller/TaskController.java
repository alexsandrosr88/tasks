package com.example.demo.controller;

import com.example.demo.dto.TaskDTORequest;
import com.example.demo.dto.TaskDTOResponse;
import com.example.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<TaskDTOResponse> criarTask(@RequestBody TaskDTORequest taskDTO){

        var task = service.criarTask(taskDTO);
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
    public TaskDTOResponse atualizaTask(@PathVariable Integer id,@RequestBody TaskDTORequest taskDTO){
        return service.atualizaTask(id, taskDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deletaTask(@PathVariable Integer id){
        service.removeTask(id);
    }
}
