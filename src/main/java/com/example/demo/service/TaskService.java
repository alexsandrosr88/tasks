package com.example.demo.service;

import com.example.demo.dto.TaskDTORequest;
import com.example.demo.dto.TaskDTOResponse;
import com.example.demo.entity.Tasks;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    @Transactional(readOnly = false)
    public TaskDTOResponse criarTask(TaskDTORequest taskDTO) {
        if (taskDTO != null) {
            return new TaskDTOResponse(repository.save(new Tasks(taskDTO)));
        }
        throw  new RuntimeException("Algo de errado aconteceu!");
    }

    @Transactional(readOnly = true)
    public List<TaskDTOResponse> listarTodasTasks() {
        return repository.findAll().stream().map(TaskDTOResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public TaskDTOResponse obterUmaTask(Integer taskId){
        return new TaskDTOResponse(repository.findById(taskId).orElseThrow());
    }

    @Transactional(readOnly = false)
    public TaskDTOResponse atualizaTask(Integer id, TaskDTORequest taskDTO){

        if(id == null || taskDTO == null){
            throw  new RuntimeException("Algo de errado aconteceu!");
        }

        Tasks taskParaAtualiza = repository.findById(id).orElseThrow();

        taskParaAtualiza.setDescription(taskDTO.getDescription());
        taskParaAtualiza.setStatus(taskDTO.getStatus());
        taskParaAtualiza.setTitle(taskDTO.getTitle());

        taskParaAtualiza.getUsers().setName(taskDTO.getUsers().getName());
        taskParaAtualiza.getUsers().setPassword(taskDTO.getUsers().getPassword());
        taskParaAtualiza.getUsers().setEmail(taskDTO.getUsers().getEmail());

        return new TaskDTOResponse(repository.save(taskParaAtualiza));
    }

    @Transactional(readOnly = false)
    public void removeTask(Integer taskId){
        Tasks tasksParaRemocao = repository.findById(taskId).orElseThrow();
        repository.delete(tasksParaRemocao);
    }
}
