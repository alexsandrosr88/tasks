package com.example.demo.service;

import com.example.demo.dto.TaskDTORequest;
import com.example.demo.dto.TaskDTOResponse;
import com.example.demo.entity.Tasks;
import com.example.demo.entity.Users;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.exception.RecursoNaoEncontrado;
import com.example.demo.service.exception.RegraDeNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Objects;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    @Autowired
    private UsersRepository usersRepository;

    @Transactional(readOnly = false)
    public TaskDTOResponse criarTask(TaskDTORequest taskDTO, BindingResult validacao) {

        validaTask(validacao);

        return new TaskDTOResponse(repository.save(new Tasks(taskDTO)));

    }

    @Transactional(readOnly = true)
    public List<TaskDTOResponse> listarTodasTasks() {
        return repository.findAll().stream().map(TaskDTOResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public TaskDTOResponse obterUmaTask(Integer taskId){
        return new TaskDTOResponse(repository.findById(taskId).orElseThrow(()->
                new RecursoNaoEncontrado("O Id da task não foi encontrado no banco de dados!")));
    }

    @Transactional(readOnly = false)
    public TaskDTOResponse atualizaUserTask(Integer userId, Integer taskId){

        Tasks taskParaAtualiza = repository.findById(taskId).orElseThrow(()->
                new RecursoNaoEncontrado("O ID da task não foi encontrado no banco de dados!"));
        Users userParaAtualiza = usersRepository.findById(userId).orElseThrow(()->
                new RecursoNaoEncontrado("O ID do user não foi encontrado no banco de dados!!"));

        taskParaAtualiza.setUsers(userParaAtualiza);

        return new TaskDTOResponse(repository.saveAndFlush(taskParaAtualiza));
    }

    @Transactional(readOnly = false)
    public TaskDTOResponse atualizaTask(Integer id, TaskDTORequest taskDTO, BindingResult validacao){

        validaTask(validacao);

        Tasks taskParaAtualiza = repository.findById(id).orElseThrow(()->
                new RecursoNaoEncontrado("O Id da tasks não foi encontrada no banco de dados!"));

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
        Tasks tasksParaRemocao = repository.findById(taskId).orElseThrow(()->
                new RecursoNaoEncontrado("O Id da task não foi encontrado no banco de dados!"));
        repository.delete(tasksParaRemocao);
    }
    private void validaTask(BindingResult validacao){
        if(validacao.hasErrors()){
            String campo = Objects.requireNonNull(validacao.getFieldError()).getField();

            switch (campo) {
                case "title", "description", "status" ->
                        throw new RegraDeNegocioException(validacao.getFieldError().getDefaultMessage());
            }
        }
    }
}
