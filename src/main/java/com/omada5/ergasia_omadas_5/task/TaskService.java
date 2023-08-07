package com.omada5.ergasia_omadas_5.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private  final SubcategoryRepository subcategoryRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository,
                       CategoryRepository categoryRepository,
                       SubcategoryRepository subcategoryRepository){
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
    }

    @GetMapping
    public List<Task> getTasks(){
        return taskRepository.findAll();
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoriesById(Long id) {
        return categoryRepository.getReferenceById(id);
    }

    public List<Subcategory> getSubCategories() {
        return subcategoryRepository.findAll();
    }

    public Subcategory getSubcategoriesById(Long id) {
        return subcategoryRepository.getReferenceById(id);
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

    public List<Task> searchTask(String search){
        return taskRepository.findTasksBySearch(search);
    }
}
