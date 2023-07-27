package com.omada5.ergasia_omadas_5.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public List<Task> getTasks(){
        return taskRepository.findAll();
    }

    public void addNewTask(Task task){
        taskRepository.save(task);
    }

    public void deleteTask(Long taskId){
        boolean exists = taskRepository.existsById(taskId);

        if(!exists)
            throw new IllegalStateException("Task with id " + taskId + " does not exist.");

        taskRepository.deleteById(taskId);
    }
}
