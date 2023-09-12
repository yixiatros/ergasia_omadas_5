package com.omada5.ergasia_omadas_5.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getTasks(){
        return taskService.getTasks();
    }

    @PostMapping
    public void registerNewTask(@RequestBody Task task){
        taskService.addNewTask(task);
    }

    @DeleteMapping(path="{taskid}")
    public void deleteTask(@PathVariable("taskid") Long taskId){
        taskService.deleteTask(taskId);
    }

    @GetMapping("/tasks")
    public String showTasks(Model model){
        List<Task> tasks = getTasks();
        List<Category> categories = taskService.getCategories();
        List<Subcategory> subcategories = taskService.getSubCategories();
        model.addAttribute("tasks", tasks);
        model.addAttribute("tasksSize", tasks.size());
        model.addAttribute("categories", categories);
        model.addAttribute("subcategories", subcategories);
        return "task_search";
    }

    @GetMapping("/task_search")
    public String searchTask(@RequestParam("keyword") String keyword,
                             @RequestParam("category") List<Long> categoryId,
                             @RequestParam("subcategory") List<Long> subcategoryId,
                             Model model){

        System.out.println("\n\n\n\n\n\n\n\n\n\n" + categoryId + "\n\n\n\n\n\n\n\n\n\n\n\n");

        List<Task> tasks = new ArrayList<>();
        if (keyword.equals(""))
            tasks = taskService.getTasks();
        else
            tasks = taskService.searchTask(keyword);

        tasks = filterTasksByCategory(tasks, categoryId);
        tasks = filterTasksBySubcategory(tasks, subcategoryId);
        List<Category> categories = taskService.getCategories();
        List<Subcategory> subcategories = taskService.getSubCategories();
        model.addAttribute("tasks", tasks);
        model.addAttribute("tasksSize", tasks.size());
        model.addAttribute("categories", categories);
        model.addAttribute("subcategories", subcategories);
        List<Category> appliedCategories = new ArrayList<>();
        for (Long id:
             categoryId) {
            appliedCategories.add(taskService.getCategoriesById(id));
        }
        model.addAttribute("appliedCategories", appliedCategories);
        List<Subcategory> appliedSubcategories = new ArrayList<>();
        for (Long id:
                subcategoryId) {
            appliedSubcategories.add(taskService.getSubcategoriesById(id));
        }
        model.addAttribute("appliedSubcategories", appliedSubcategories);
        return "task_search";
    }

    private List<Task> filterTasksByCategory(List<Task> tasks, List<Long> categoryId){
        if (categoryId.get(0) == -1)
            categoryId.remove(0);

        if (categoryId.size() == 0)
            return tasks;

        List<Category> categories = new ArrayList<>();
        for (Long id:
             categoryId) {
            categories.add(taskService.getCategoriesById(id));
        }


        List<Task> newTasks = new ArrayList<>();
        for (Task task:
             tasks) {

            for (Category category:
                 categories) {
                if (task.getCategory() == category){
                    newTasks.add(task);
                    break;
                }
            }

        }

        return newTasks;
    }

    private List<Task> filterTasksBySubcategory(List<Task> tasks, List<Long> subcategoryId) {
        if (subcategoryId.get(0) == -1)
            subcategoryId.remove(0);

        if (subcategoryId.size() == 0)
            return tasks;

        List<Subcategory> subcategories = new ArrayList<>();
        for (Long id:
                subcategoryId) {
            subcategories.add(taskService.getSubcategoriesById(id));
        }

        List<Task> newTasks = new ArrayList<>();
        for (Task task:
                tasks) {

            for (Subcategory subcategory :
                    subcategories) {
                if (task.getSubcategory() == subcategory) {
                    newTasks.add(task);
                    break;
                }
            }
        }

        return newTasks;
    }

    @GetMapping("/task_create")
    public String createTask(Model model){
        model.addAttribute("currentDateTime", LocalDate.now());
        List<Category> categories = taskService.getCategories();
        List<Subcategory> subcategories = taskService.getSubCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("subcategories", subcategories);
        return "task_create";
    }

    public List<Subcategory> getSubcategoriesByCategories(Long categoryId){
        List<Subcategory> subcategories = taskService.getSubCategories();
        List<Subcategory> newSubcategories = new ArrayList<>();
        for (Subcategory sub:
             subcategories) {
            if (sub.getCategory() != null)
                if (Objects.equals(sub.getCategory().getId(), categoryId))
                    newSubcategories.add(sub);
        }
        return newSubcategories;
    }

    @PostMapping("/task_create")
    public String setCreatedTask(@RequestParam("title") String title,
                        @RequestParam("description") String description,
                        @Param("isPublic") boolean isPublic,
                        @Param("showPrice") boolean showPrice,
                        @Param("isPayingByTheHour") boolean isPayingByTheHour,
                        @RequestParam("maxPrice") float maxPrice,
                        @RequestParam("endDate") LocalDateTime endDate,
                        @RequestParam("category") String categoryId,
                        @RequestParam(value = "subcategory", required = false, defaultValue = "-1") String subcategoryId){
        Optional<Task> userOptional = taskService.getTasks().stream()
                .filter(u->title.equals(u.getTitle()))
                .findAny();

        if (userOptional.isPresent())
            return "task_search";

        Task task = new Task(
                title,
                description,
                isPublic,
                showPrice,
                isPayingByTheHour,
                maxPrice,
                endDate
        );

        Category category = taskService.getCategoriesById(Long.valueOf(categoryId));
        task.setCategory(category);

        if (!subcategoryId.equals("-1")){
            Subcategory subcategory = taskService.getSubcategoriesById(Long.valueOf(subcategoryId));
            task.setSubcategory(subcategory);
        }

        taskService.addNewTask(task);

        return "index";
    }

    @GetMapping(path = "/task_view/{taskId}")
    public String seeTask(@PathVariable("taskId") Long taskId, Model model){
        Optional<Task> optionalTask = taskService.getTaskById(taskId);
        Task task = null;
        if (optionalTask.isPresent())
            task = optionalTask.get();

        model.addAttribute("task", task);
        model.addAttribute("endDate", task.getEndDate());
        return "task_view";
    }
}
